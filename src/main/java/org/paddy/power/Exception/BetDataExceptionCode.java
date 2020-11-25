package org.paddy.power.Exception;

public enum BetDataExceptionCode {

        PARAMETER_NULL_INSIDE_CODE(2000, "Parameter passed in the method is null");

        private final int errorCode;
        private final String errorMessage;

        BetDataExceptionCode(final int errorCode, final String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

}
