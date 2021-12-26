package dev.jaym21.newsnow.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, parameters: WorkerParameters): Worker(context, parameters) {

    override fun doWork(): Result {

    }
}