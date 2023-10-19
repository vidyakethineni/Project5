package com.example.project5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project5.databinding.FragmentTranslationBinding
import com.google.mlkit.nl.translate.TranslateLanguage

/**
 * A Fragment for translating text using Google ML Kit Translation API.
 */
class TranslationFragment : Fragment() {
    /**
     * Binding object for the TranslationFragment layout.
     */
    private var _binding: FragmentTranslationBinding? = null
    /**
     * Getter for the binding object.
     */
    private val binding get() = _binding!!
    /**
     * View model for managing translation-related operations.
     */
    private lateinit var viewModel: TranslationViewModel

    /**
     * Initializing the Fragment's view, binding, and view model.
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTranslationBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(TranslationViewModel::class.java)

        var inputText: String = ""

        /**
         * Initializing UI component from fragment_translation.xml
         *
         */
        /**
         * Saving the selected radio group button from sourceLanguageRadioGroup as the source language
         */
        binding.sourceLanguageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            var sourceLanguage = when (checkedId) {
                R.id.englishRadioBtn -> TranslateLanguage.ENGLISH
                R.id.spanishRadioBtn -> TranslateLanguage.SPANISH
                R.id.germanRadioBtn -> TranslateLanguage.GERMAN
                R.id.detectRadioBtn -> "Detect"
                else -> TranslateLanguage.ENGLISH
            }
            viewModel.sourceLanguage = sourceLanguage
            Log.i("sourceLanguageFragment",sourceLanguage)
        }

        /**
         * Saving the selected radio group button from targetLanguageRadioGroup as the target language
         */
        binding.targetLanguageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            var targetLanguage = when (checkedId) {
                R.id.englishTargetRadioBtn -> TranslateLanguage.ENGLISH
                R.id.spanishTargetRadioBtn -> TranslateLanguage.SPANISH
                R.id.germanTargetRadioBtn -> TranslateLanguage.GERMAN
                else -> TranslateLanguage.SPANISH
            }
            viewModel.targetLanguage = targetLanguage
            Log.i("targetLanguageFragment",targetLanguage)

        }
        viewModel.Translator(inputText)

        /**
         * Handles user input and translates user input by calling on functions from the view model
         */
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
                viewModel.Translator(inputText)
                viewModel.translateInput(inputText) { translatedText ->
                    binding.translationTextView.text = translatedText //Updating text view with translated text
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        return view
    }
    /**
     * Called when the view associated with this Fragment is destroyed.
     */
    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}
