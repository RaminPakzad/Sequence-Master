package com.sadad.sw.sequencemaster;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.sadad.sw.sequencemaster.service.SequenceCatcher;
import com.sadad.sw.sequencemaster.service.SequenceDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.GsonJsonParser;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class SequenceMasterApplication implements CommandLineRunner {
    @Autowired
    private SequenceDetector sequenceDetector;
    @Autowired
    private SequenceCatcher sequenceCatcher;

    public static void main(String[] args) {
        SpringApplication.run(SequenceMasterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (sequenceDetector.isItContainsSequence(sampleSequence)) {
            String sequence = sequenceCatcher.getSequence(sampleSequence);
            System.out.println(sequence);
        }

    }

    private final String sampleSequence = "define(function (require, exports, module) {" +
            "    'use strict';" +
            "    // @ngInject" +
            "    exports.fundTransferAmountSrv = function (lpEvent, lpState, lpChannel, lpStorage, lpAtmState, lpTransferType, lpTagData) {" +
            "        return {" +
            "            inputAmount: function (sendSeqItem, ensueTlvArr) {" +
            "                let pin = lpTagData.get(ensueTlvArr, lpTagData.RETURN_VALUE);" +
            "                let amount = lpStorage.getAmount().toString();" +
            "                if (pin === 'Bs' && pin.length > 0) {" +
            "                    lpStorage.setAmount(amount.substring(0, amount.length - 1));" +
            "                } else if (pin === 'Cl') {" +
            "                    lpStorage.setAmount(\"\");" +
            "                } else if (pin === 0 && amount.length !== 0) {" +
            "                    amount += pin;" +
            "                    lpStorage.setAmount(amount);" +
            "                } else if (pin !== 0) {" +
            "                    amount += pin;" +
            "                    lpStorage.setAmount(amount);" +
            "                }" +
            "                return sendSeqItem;" +
            "            }," +
            "            checkInputAmount: function (sendSeqItem, ensueTlvArr) {" +
            "                let copySendSeqItem = JSON.parse(JSON.stringify(sendSeqItem));" +
            "                if (lpStorage.getAmount() > 0 ) {" +
            "                    copySendSeqItem.next = \"1-8\";" +
            "                } else {" +
            "                    copySendSeqItem.next = \"2\";" +
            "                }" +
            "                return copySendSeqItem;" +
            "            }," +
            "            timeoutStep: function () {" +
            "                if (lpAtmState.subState === lpState.FUND_TRANSFER_AMOUNT) {" +
            "                    return \"4-3\";" +
            "                } else { // (lpAtmState.subState === lpState.FUND_TRANSFER_AMOUNT_INCORRECT) {" +
            "                    return \"4-4\";" +
            "                }" +
            "            }," +
            "            isTransferToOwnAccount: function () {" +
            "                return lpStorage.getTransferType() === lpTransferType.TO_OWN_ACCOUNT;" +
            "            }," +
            "            sequence : [" +
            "                // Main routine" +
            "                {send: [\"0X10\", \"140\", \"0X11\", \"20\", \"0X15\", \"0\", \"0X20\", \"110000000111111111111111\", \"0X21\", \"110000000011000000000000\"], step: \"1\", next: \"1-1\"}," +
            "                {receive: [\"0X10\", \"141\", \"0X12\", \"000\"], subState : lpState.FUND_TRANSFER_AMOUNT, step: \"1-1\", next: \"1-2\"}," +
            "                {event:lpEvent.CHANGE_PAGE, widget:'fundTransfer', page:'fundTransferAmount', step:\"1-2\", next:\"1-3\"}," +
            "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\", new RegExp(\"^0{1,3}$|^[1-9]$|^Bs$|^Cl$\")], step: \"1-3\", next: \"1-4\"}," +
            "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], forge:\"inputAmount\", ensue:\"1-3\", port:2000, step: \"1-4\", next: \"1-5\"}," +
            "                {event:lpEvent.PUBLISH_CHANNEL, channel:lpChannel.FUND_TRANSFER_AMOUNT, action:\"inputAmount\", ensue:\"1-3\", step:\"1-5\", next:\"1-6\"}," +
            "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\",  new RegExp(\"^En$|^F8$\")], step: \"1-6\", next: \"1-7\"}," +
            "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], forge:\"checkInputAmount\", port:2000, step: \"1-7\", next: \"1-8\"}," +
            "                {event: lpEvent.SET_RETURN, returnState: lpState.FUND_TRANSFER_AMOUNT, returnStep: \"1-10\", step:\"1-8\", next:\"1-9\"}," +
            "                {event: lpEvent.CHANGE_STATE, state: lpState.ACCOUNT_LIST, step: \"1-9\", next: \"1\"}," +
            "                {event: lpEvent.CONDITION, check: 'isTransferToOwnAccount', trueNext:\"1-11\" , falseNext:\"1-13\", step: \"1-10\" }," +
            "                {event: lpEvent.SET_RETURN, returnState: lpState.FUND_TRANSFER_PAYER_ID, returnStep: \"1\", step:\"1-11\", next:\"1-12\"}," +
            "                {event: lpEvent.CHANGE_STATE, state: lpState.ACCOUNT_LIST, step: \"1-12\", next: \"1\"}," +
            "                {event: lpEvent.CHANGE_STATE, state: lpState.FUND_TRANSFER_PAYER_ID, step: \"1-13\", next:\"1\"}," +
            "" +
            "" +
            "" +
            "                // Transfer amount incorrect" +
            "                {send: [\"0X10\", \"140\", \"0X11\", \"20\", \"0X15\", \"0\", \"0X20\", \"110000000111111111111111\", \"0X21\", \"110000000011000000000000\"], step: \"2\", next: \"2-1\"}," +
            "                {event: lpEvent.CHANGE_SUB_STATE, subState: lpState.FUND_TRANSFER_AMOUNT_INCORRECT, step: \"2-1\", next:\"2-2\"}," +
            "                {receive: [\"0X10\", \"141\", \"0X12\", \"000\"], subState: lpState.FUND_TRANSFER_AMOUNT_INCORRECT,step: \"2-2\", next: \"2-3\"}," +
            "                {event:lpEvent.CHANGE_PAGE, widget:'fundTransfer', page:'fundTransferAmountIncorrect', step:\"2-3\", next:\"1-3\"}," +
            "" +
            "                // Return to preview step" +
            "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\", \"F7\"], step: \"3\", next: \"3-1\"}," +
            "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], port:2000, step: \"3-1\", next: \"3-2\"}," +
            "                {event: lpEvent.CHANGE_STATE , state: lpState.SELECT_SERVICE, step:\"3-2\", next:\"1\"}," +
            "" +
            "                // TimeOut" +
            "                {receive: [\"0X10\", \"142\", \"0X12\", \"002\"], step: \"4\", next: \"4-1\"}," +
            "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], port:2000, step: \"4-1\", next: \"4-2\"}," +
            "                {event: lpEvent.CONDITION, check: 'timeoutStep', step: \"4-2\"}," +
            "                {event: lpEvent.CHANGE_STATE, state: lpState.TIMEOUT, args: {backStep:\"1\"}, step: \"4-3\", next:\"1\"}," +
            "                {event: lpEvent.CHANGE_STATE, state: lpState.TIMEOUT, args: {backStep:\"2\"}, step: \"4-4\", next:\"1\"}," +
            "" +
            "                // Cancel" +
            "                {receive: [\"0X10\", \"142\", \"0X12\", \"000\", \"0X13\", \"Ca\"], step: \"5\", next: \"5-1\"}," +
            "                {send: [\"0X10\", \"143\", \"0X12\", \"000\"], port:2000, step: \"5-1\", next: \"5-2\"}," +
            "                {event: lpEvent.CHANGE_STATE, state: lpState.EXIT_CARD, step: \"5-2\", next:\"1\"}," +
            "            ]" +
            "        }" +
            "    }" +
            "});";
}
