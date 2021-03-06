package com.QwertyNetworks.ai_speaker.ui.main.media

import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import java.lang.Exception

class MediaRecorderVoice(private var audioSavePath: String,
                         private var mediaRecorder: MediaRecorder,
                         private var context: Context
) {



    fun mediaRecorder(path: String){
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setOutputFile(path)

    }

    fun startRecorder() {
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: IOException) {
            e.stackTrace
        }
    }
    fun stopRecorder() {
       try {
           mediaRecorder.stop()
           mediaRecorder.reset()
           mediaRecorder.release()
       } catch (e: IOException) {
           e.stackTrace
       }
    }
}