package com.sadad.sw.sequencemaster;

import com.sadad.sw.sequencemaster.service.SequenceDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SequenceMasterApplication implements CommandLineRunner {
    @Autowired
    private SequenceDetector sequenceDetector;

    public static void main(String[] args) {
        SpringApplication.run(SequenceMasterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sequenceDetector.isItContainsSequence("define(function (require, exports, module) {\n" +
                "    'use strict';\n" +
                "    // @ngInject\n" +
                "    exports.fundTransferAmountSrv = function (lpEvent, lpState, lpChannel, lpStorage, lpAtmState, lpTransferType, lpTagData) {\n" +
                "        return {\n" +
                "            inputAmount: function (sendSeqItem, ensueTlvArr) {\n" +
                "                let pin = lpTagData.get(ensueTlvArr, lpTagData.RETURN_VALUE);\n" +
                "                let amount = lpStorage.getAmount().toString();\n" +
                "                if (pin === 'Bs' && pin.length > 0) {\n" +
                "                    lpStorage.setAmount(amount.substring(0, amount.length - 1));\n" +
                "                } else if (pin === 'Cl') {\n" +
                "                    lpStorage.setAmount(\"\");\n" +
                "                } else if (pin === 0 && amount.length !== 0) {\n" +
                "                    amount += pin;\n" +
                "                    lpStorage.setAmount(amount);\n" +
                "                } else if (pin !== 0) {\n" +
                "                    amount += pin;\n" +
                "                    lpStorage.setAmount(amount);\n" +
                "                }\n" +
                "                return sendSeqItem;\n" +
                "            },\n" +
                "            checkInputAmount: function (sendSeqItem, ensueTlvArr) {\n" +
                "                let copySendSeqItem = JSON.parse(JSON.stringify(sendSeqItem));\n" +
                "                if (lpStorage.getAmount() > 0 ) {\n" +
                "                    copySendSeqItem.next = \"1-8\";\n" +
                "                } else {\n" +
                "                    copySendSeqItem.next = \"2\";\n" +
                "                }\n" +
                "                return copySendSeqItem;\n" +
                "            },\n" +
                "            timeoutStep: function () {\n" +
                "                if (lpAtmState.subState === lpState.FUND_TRANSFER_AMOUNT) {\n" +
                "                    return \"4-3\";\n" +
                "                } else { // (lpAtmState.subState === lpState.FUND_TRANSFER_AMOUNT_INCORRECT) {\n" +
                "                    return \"4-4\";\n" +
                "                }\n" +
                "            },\n" +
                "            isTransferToOwnAccount: function () {\n" +
                "                return lpStorage.getTransferType() === lpTransferType.TO_OWN_ACCOUNT;\n" +
                "            },\n" +
                "            sequence : [\n" +
                "                // Main routine\n" +
                "                {send: [\"0X10\", \"140\", \"0X11\", \"20\", \"0X15\", \"0\", \"0X20\", \"110000000111111111111111\", \"0X21\", \"110000000011000000000000\"], step: \"1\", next: \"1-1\"},\n" +
                "                {receive: [\"0X10\", \"141\", \"0X12\", \"000\"], subState : lpState.FUND_TRANSFER_AMOUNT, step: \"1-1\", next: \"1-2\"},\n" +
                "                {event:lpEvent.CHANGE_PAGE, widget:'fundTransfer', page:'fundTransferAmount', step:\"1-2\", next:\"1-3\"},\n" +
                "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\", new RegExp(\"^0{1,3}$|^[1-9]$|^Bs$|^Cl$\")], step: \"1-3\", next: \"1-4\"},\n" +
                "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], forge:\"inputAmount\", ensue:\"1-3\", port:2000, step: \"1-4\", next: \"1-5\"},\n" +
                "                {event:lpEvent.PUBLISH_CHANNEL, channel:lpChannel.FUND_TRANSFER_AMOUNT, action:\"inputAmount\", ensue:\"1-3\", step:\"1-5\", next:\"1-6\"},\n" +
                "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\",  new RegExp(\"^En$|^F8$\")], step: \"1-6\", next: \"1-7\"},\n" +
                "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], forge:\"checkInputAmount\", port:2000, step: \"1-7\", next: \"1-8\"},\n" +
                "                {event: lpEvent.SET_RETURN, returnState: lpState.FUND_TRANSFER_AMOUNT, returnStep: \"1-10\", step:\"1-8\", next:\"1-9\"},\n" +
                "                {event: lpEvent.CHANGE_STATE, state: lpState.ACCOUNT_LIST, step: \"1-9\", next: \"1\"},\n" +
                "                {event: lpEvent.CONDITION, check: 'isTransferToOwnAccount', trueNext:\"1-11\" , falseNext:\"1-13\", step: \"1-10\" },\n" +
                "                {event: lpEvent.SET_RETURN, returnState: lpState.FUND_TRANSFER_PAYER_ID, returnStep: \"1\", step:\"1-11\", next:\"1-12\"},\n" +
                "                {event: lpEvent.CHANGE_STATE, state: lpState.ACCOUNT_LIST, step: \"1-12\", next: \"1\"},\n" +
                "                {event: lpEvent.CHANGE_STATE, state: lpState.FUND_TRANSFER_PAYER_ID, step: \"1-13\", next:\"1\"},\n" +
                "\n" +
                "\n" +
                "\n" +
                "                // Transfer amount incorrect\n" +
                "                {send: [\"0X10\", \"140\", \"0X11\", \"20\", \"0X15\", \"0\", \"0X20\", \"110000000111111111111111\", \"0X21\", \"110000000011000000000000\"], step: \"2\", next: \"2-1\"},\n" +
                "                {event: lpEvent.CHANGE_SUB_STATE, subState: lpState.FUND_TRANSFER_AMOUNT_INCORRECT, step: \"2-1\", next:\"2-2\"},\n" +
                "                {receive: [\"0X10\", \"141\", \"0X12\", \"000\"], subState: lpState.FUND_TRANSFER_AMOUNT_INCORRECT,step: \"2-2\", next: \"2-3\"},\n" +
                "                {event:lpEvent.CHANGE_PAGE, widget:'fundTransfer', page:'fundTransferAmountIncorrect', step:\"2-3\", next:\"1-3\"},\n" +
                "\n" +
                "                // Return to preview step\n" +
                "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\", \"F7\"], step: \"3\", next: \"3-1\"},\n" +
                "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], port:2000, step: \"3-1\", next: \"3-2\"},\n" +
                "                {event: lpEvent.CHANGE_STATE , state: lpState.SELECT_SERVICE, step:\"3-2\", next:\"1\"},\n" +
                "\n" +
                "                // TimeOut\n" +
                "                {receive: [\"0X10\", \"142\", \"0X12\", \"002\"], step: \"4\", next: \"4-1\"},\n" +
                "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], port:2000, step: \"4-1\", next: \"4-2\"},\n" +
                "                {event: lpEvent.CONDITION, check: 'timeoutStep', step: \"4-2\"},\n" +
                "                {event: lpEvent.CHANGE_STATE, state: lpState.TIMEOUT, args: {backStep:\"1\"}, step: \"4-3\", next:\"1\"},\n" +
                "                {event: lpEvent.CHANGE_STATE, state: lpState.TIMEOUT, args: {backStep:\"2\"}, step: \"4-4\", next:\"1\"},\n" +
                "\n" +
                "                // Cancel\n" +
                "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\", \"Ca\"], step: \"5\", next: \"5-1\"},\n" +
                "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], port:2000, step: \"5-1\", next: \"5-2\"},\n" +
                "                {event: lpEvent.CHANGE_STATE, state: lpState.EXIT_CARD, step: \"5-2\", next:\"1\"},\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "});");
    }
}
