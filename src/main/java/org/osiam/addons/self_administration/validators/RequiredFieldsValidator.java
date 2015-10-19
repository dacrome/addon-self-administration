/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.addons.self_administration.validators;

import org.osiam.addons.self_administration.Config;
import org.osiam.addons.self_administration.registration.HtmlField;
import org.osiam.addons.self_administration.registration.RegistrationUser;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

public class RequiredFieldsValidator implements ConstraintValidator<RequiredFields, RegistrationUser> {

    @Autowired
    private Config config;

    @Override
    public void initialize(RequiredFields constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegistrationUser user, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, HtmlField> allowedFields = config.getAllAllowedFields();

        BeanWrapper registrationUserBeanWrapper = new BeanWrapperImpl(user);

        for (String field : allowedFields.keySet()) {
            if (isFieldInvalid(registrationUserBeanWrapper.getPropertyValue(field).toString(), allowedFields.get(field).isRequired())) {
                return false;
            }
        }

        return true;
    }

    private boolean isFieldInvalid(String field, boolean required) {
        return required && isNullOrEmpty(field);
    }
}
