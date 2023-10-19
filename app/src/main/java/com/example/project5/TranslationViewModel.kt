package com.example.project5

import androidx.lifecycle.ViewModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions

/**
 * ViewModel for managing translation functions
 */
class TranslationViewModel : ViewModel() {

    /**
     * Translator object for language translation.
     */
    lateinit var languageTranslator: Translator
    /**
     * The source language for translation.
     */
    var sourceLanguage = String()
    /**
     * The target language for translation.
     */
    var targetLanguage = String()


    /**
     * Initializing the Translator() function
     * which has translator objects that are configured with the source and target language
     * and calls helper functions to download translation models
     */
    fun Translator(input: String) {
        if (sourceLanguage == "Detect") {
            val languageIdentifier = LanguageIdentification.getClient()
            sourceLanguage = languageIdentifier.identifyLanguage(input).toString()
            LanguageIdentification.getClient(
                LanguageIdentificationOptions.Builder()
                    .setConfidenceThreshold(0.34f)
                    .build()
            )
        } else {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage)
                .setTargetLanguage(targetLanguage)
                .build()
            languageTranslator = Translation.getClient(options)
            downloadModelIfNeeded()
        }
    }

    /**
     * Downloads the required translation model if it's not already available on the device.
     */
    fun downloadModelIfNeeded() {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        languageTranslator.downloadModelIfNeeded(conditions)
    }

    /**
     * Translates the input text and provides the translated result through the callback.
     */
    fun translateInput(input: String, translationCallback: (String) -> Unit) {
        languageTranslator.translate(input)
            .addOnSuccessListener { translatedText ->
                translationCallback(translatedText)
            }
    }
}

