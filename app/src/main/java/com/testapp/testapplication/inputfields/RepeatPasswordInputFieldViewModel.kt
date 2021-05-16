package com.testapp.testapplication.inputfields

class RepeatPasswordInputFieldViewModel(val basePasswordField: PasswordInputFieldViewModel) : PasswordInputFieldViewModel(basePasswordField.initInput) {

    override fun validate() : Boolean {
        if(basePasswordField.text.get() != text.get()) {
            validationError.set(ValidationError.PasswordsEquality)
            return false
        }
        else {
            return super.validate()
        }
    }
}