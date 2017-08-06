package com.jirapave.cli.common.validators;

import com.jirapave.cli.common.exceptions.ValidatorException;
import com.jirapave.cli.persistence.model.Payment;

/**
 * Validator for payment VO
 */
@Deprecated
public class PaymentValidator extends ValidatorBase {

    public static boolean validate(Payment payment) throws ValidatorException {
        if (payment == null){
            throw new ValidatorException("Payment is null - cannot proceed");
        }

        if (payment.getAmount() == null){
            throw new ValidatorException("Amount is null");
        }

        if (payment.getCurrency() == null || payment.getCurrency().isEmpty()){
            throw new ValidatorException("Currency is null or empty");
        }

        return true;
    }

}
