package com.fujitsu.app.domain;

import com.fujitsu.app.domain.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.http.HttpStatus;

/**
 * Object representing an extra fee related to weather conditions.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraFee {

    @Id
    private Long id;

    private Long vehicleTypeId;

    private String condition;

    private double amount;

    private String errorMessage;

    /**
     * Evaluates the extra fee condition against a weather observation.
     *
     * @param observation weather observation to evaluate condition against
     * @return extra fee on successful match, otherwise return 0
     * @throws AppException on successful match if the extra fee object includes an error message
     * @throws ExpressionException when trying to parse an invalid condition
     */
    public double evaluateCondition(Observation observation) throws AppException, ExpressionException {
        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext ctx = SimpleEvaluationContext
                .forReadOnlyDataBinding()
                .withInstanceMethods()
                .build();

        Expression parsed = parser.parseExpression(condition);
        boolean matchFound = Boolean.TRUE.equals(parsed.getValue(ctx, observation, Boolean.class));

        if (matchFound & !errorMessage.isBlank()) {
            throw new AppException(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return matchFound ? amount : 0;
    }
}
