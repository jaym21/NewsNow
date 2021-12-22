package dev.jaym21.newsnow.data

import dev.jaym21.newsnow.utils.Resource
import kotlinx.coroutines.flow.*

fun <ResultType, RequestType>networkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> RequestType, //fetching data from api
    saveFetchedResults: suspend (RequestType) -> Unit, //saving the fetched data from api in local db
    shouldFetch: (ResultType) -> Boolean = { true }  //to check if we need to fetch data from api
) = flow {
    //this is a cold flow i.e it will not execute until a receiver starts collecting from it

    //getting data from query
    val data = query().first() //only taking the first data emitted to get the ResultType of data

    //checking if data is to be fetched from api
    val flow = if (shouldFetch(data)) {

        //as data is to be fetched from api, first showing loading by emitting loading from resource and passing data i.e cache data if available
        emit(Resource.Loading(data))

        //surround fetch request in try catch to catch error due to internet connection or error from server
        try {
            //fetching data from api and saving into local db
            saveFetchedResults(fetch())

            //adding all data from api call in query by wrapping it in resource success
            query().map {
                Resource.Success(it) //Flow<Resource<List<Article>
            }
        }catch (throwable: Throwable) {
            //adding error from api or due to internet in query
            query().map {
                Resource.Error(throwable, it)
            }
        }
    } else {
        //getting data from  db and adding to query
        query().map {
            Resource.Success(it)
        }
    }

    //emitting all the data from either api call or database
    emitAll(flow)
}