package com.example.own.service.excel.impl;

import com.example.own.service.excel.IExcelExportService;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelExportServiceImpl implements IExcelExportService {


    public static void main(String[] args) throws Exception {

        String value = "{\n" +
                "        \"pd.idleMode\": 0,\n" +
                "        \"pd.midWindSpeedCnt\": 528,\n" +
                "        \"power.acFreq\": 50,\n" +
                "        \"power.batVolt\": 5898,\n" +
                "        \"pd.pdSubMode\": 3,\n" +
                "        \"pd.lowWindSpeedCnt\": 490,\n" +
                "        \"motor.power\": 5,\n" +
                "        \"pd.batChgStatus\": 0,\n" +
                "        \"motor.motorFsmState\": 5,\n" +
                "        \"motor.windTime\": 461838,\n" +
                "        \"motor.threeWayState\": 0,\n" +
                "        \"pd.tempDisplay\": 0,\n" +
                "        \"motor.vBus\": 14860,\n" +
                "        \"power.mpptLockFlag\": 0,\n" +
                "        \"bms.bmsDisplayTime\": 0,\n" +
                "        \"pd.ver\": 0,\n" +
                "        \"pd.heatEnv\": 2493,\n" +
                "        \"bms.maxCellVol\": 0,\n" +
                "        \"bms.bmsBatErrCode\": 0,\n" +
                "        \"pd.dmPowerSupplyCnt\": 0,\n" +
                "        \"motor.mosTemp\": 35,\n" +
                "        \"power.acWattsRange0Time\": 3571384,\n" +
                "        \"pd.mpptPwr\": 0,\n" +
                "        \"bms.bmsSoc\": 0,\n" +
                "        \"pd.CompressorTempCnt\": 0,\n" +
                "        \"motor.hpProtFlg\": 0,\n" +
                "        \"pd.batVolt\": 5894,\n" +
                "        \"power.mpptVolRange0Time\": 0,\n" +
                "        \"pd.envTempRangeCnt\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            209,\n" +
                "            49,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"power.powerResv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"power.batCurr\": 0,\n" +
                "        \"motor.motorResv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.fanValue\": 0,\n" +
                "        \"pd.busVol\": 4200,\n" +
                "        \"power.acWattsRange4Time\": 0,\n" +
                "        \"motor.frontFanWorkTime\": 437833,\n" +
                "        \"motor.compressorWorkTime\": 982,\n" +
                "        \"pd.powerMode\": 1,\n" +
                "        \"motor.serveFsmState\": 4,\n" +
                "        \"motor.coolSleepTime\": 0,\n" +
                "        \"motor.setCondFanRpm\": 0,\n" +
                "        \"power.errCode\": 0,\n" +
                "        \"pd.batSoc\": 0,\n" +
                "        \"motor.setEvapFanRpm\": 800,\n" +
                "        \"power.runSts\": 7,\n" +
                "        \"pd.deviceName\": \"\",\n" +
                "        \"power.pfcOcpS\": 0,\n" +
                "        \"power.fanSts\": 25,\n" +
                "        \"pd.tempSys\": 0,\n" +
                "        \"power.mpptVolRange3Time\": 0,\n" +
                "        \"pd.sacWorkTime\": 675911,\n" +
                "        \"bms.remainCap\": 0,\n" +
                "        \"pd.pdTempSys\": 0,\n" +
                "        \"pd.bmsBoundFlag\": 0,\n" +
                "        \"pd.condTemp\": 2502,\n" +
                "        \"power.batPwrOut\": 0,\n" +
                "        \"motor.ecoStopFlag\": 0,\n" +
                "        \"pd.wtePumpSpd\": 0,\n" +
                "        \"bms.bmsCur\": 0,\n" +
                "        \"pd.wteFul\": 0,\n" +
                "        \"power.mpptVolRange1Time\": 0,\n" +
                "        \"power.mpptWork\": 0,\n" +
                "        \"power.resv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"motor.fourWaySwitchCnt\": 4,\n" +
                "        \"pd.beepEn\": 0,\n" +
                "        \"pd.runSts\": 7,\n" +
                "        \"motor.waterValue\": 272,\n" +
                "        \"pd.batCurr\": 0,\n" +
                "        \"pd.dp2PowerSupplyCnt\": 0,\n" +
                "        \"pd.rlySts\": 2,\n" +
                "        \"bms.chgWattRangeTime\": [\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295,\n" +
                "            4294967295\n" +
                "        ],\n" +
                "        \"power.acPowerSupplyCnt\": 1946,\n" +
                "        \"pd.setTempCel\": 24,\n" +
                "        \"pd.pvPower\": 0,\n" +
                "        \"pd.hotSleepCnt\": 36,\n" +
                "        \"power.acWattsRange1Time\": 31769,\n" +
                "        \"pd.timeEn\": 0,\n" +
                "        \"pd.batPwrOut\": 0,\n" +
                "        \"motor.drainageTime\": 2150,\n" +
                "        \"motor.hotNormalTime\": 61,\n" +
                "        \"pd.bmsPid\": 0,\n" +
                "        \"bms.sleepCnt\": 65535,\n" +
                "        \"pd.acFreq\": 50,\n" +
                "        \"pd.mpptVol\": 195,\n" +
                "        \"pd.acCurrRms\": 5870,\n" +
                "        \"bms.xt150AccessCnt\": 65535,\n" +
                "        \"power.acVoltRms\": 232,\n" +
                "        \"power.mpptWattsRange0Time\": 0,\n" +
                "        \"motor.setEleExpansStep\": 0,\n" +
                "        \"pd.setFanVal\": 0,\n" +
                "        \"pd.batPowerSupplyTime\": 0,\n" +
                "        \"power.busVol\": 4200,\n" +
                "        \"power.acPwrIn\": 0,\n" +
                "        \"pd.hotNormalCnt\": 384,\n" +
                "        \"pd.wteCnt\": 0,\n" +
                "        \"pd.acPwrIn\": 0,\n" +
                "        \"motor.hotSleepTime\": 0,\n" +
                "        \"pd.coolMaxCnt\": 42,\n" +
                "        \"bms.awakeCnt\": 65535,\n" +
                "        \"pd.frontInTempErrCnt\": 0,\n" +
                "        \"bms.maxCellTemp\": 0,\n" +
                "        \"pd.evaOut\": 8.3,\n" +
                "        \"bms.powerOnCnt\": 65535,\n" +
                "        \"power.pvOcpHw\": 0,\n" +
                "        \"power.acWattsRange5Time\": 0,\n" +
                "        \"pd.conTube\": 4.0,\n" +
                "        \"pd.bmsUnderVoltage\": 0,\n" +
                "        \"bms.bmsType\": 0,\n" +
                "        \"pd.tempNtc\": 50,\n" +
                "        \"power.carPowerSupplyCnt\": 0,\n" +
                "        \"pd.envTemp\": 22.5,\n" +
                "        \"pd.waterValue\": 0,\n" +
                "        \"motor.drainageCnt\": 5,\n" +
                "        \"power.mpptCur\": 0,\n" +
                "        \"bms.minCellVol\": 0,\n" +
                "        \"pd.sacIdleTime\": 353406,\n" +
                "        \"pd.exhTube\": 3.9,\n" +
                "        \"pd.acVoltRms\": 228,\n" +
                "        \"pd.dp2PowerSupplyTime\": 0,\n" +
                "        \"motor.evapFanRpm\": 592,\n" +
                "        \"power.mpptSts\": 0,\n" +
                "        \"pd.coolNormalCnt\": 404,\n" +
                "        \"bms.bmsReqVol\": 0,\n" +
                "        \"pd.coolEnv\": 2068,\n" +
                "        \"motor.frontFanBlockCnt\": 0,\n" +
                "        \"pd.setTemp\": 24,\n" +
                "        \"pd.batChgRemain\": 0,\n" +
                "        \"pd.coolTemp\": 20.7,\n" +
                "        \"pd.powerOffCounts\": 131,\n" +
                "        \"pd.sacWattRangeTime\": [\n" +
                "            897260,\n" +
                "            67,\n" +
                "            884,\n" +
                "            33,\n" +
                "            1,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.errPowerCommCnt\": 0,\n" +
                "        \"pd.mpptCur\": 0,\n" +
                "        \"pd.backPipeTempErrCnt\": 0,\n" +
                "        \"pd.mpptSts\": 0,\n" +
                "        \"motor.fourWayWorkTime\": 91,\n" +
                "        \"motor.mtrLogicErr\": 0,\n" +
                "        \"motor.focId\": 0,\n" +
                "        \"motor.protFlag\": 0,\n" +
                "        \"bms.bmsDsgTime\": 0,\n" +
                "        \"bms.bmsHwFlag\": 0,\n" +
                "        \"pd.dpPowerSupplyCnt\": 0,\n" +
                "        \"power.errLock\": 0,\n" +
                "        \"pd.mpptWork\": 0,\n" +
                "        \"power.mpptVolRange2Time\": 0,\n" +
                "        \"pd.powerOnCounts\": 257,\n" +
                "        \"motor.compressorRpm\": 0,\n" +
                "        \"power.mpptWattsRange1Time\": 0,\n" +
                "        \"motor.coolMaxTime\": 6,\n" +
                "        \"bms.minCellTemp\": 0,\n" +
                "        \"pd.idleTime\": 0,\n" +
                "        \"pd.hotEcoCnt\": 37,\n" +
                "        \"pd.pdResv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.mainMode\": 0,\n" +
                "        \"bms.bmsSwFlag\": 0,\n" +
                "        \"power.busCurr\": 0,\n" +
                "        \"motor.pMtrCnt\": 689,\n" +
                "        \"pd.busVolt\": 4200,\n" +
                "        \"pd.bmsErr\": 0,\n" +
                "        \"pd.refEn\": 0,\n" +
                "        \"pd.errAllCnt\": 0,\n" +
                "        \"pd.pdMainMode\": 0,\n" +
                "        \"pd.busCurr\": 0,\n" +
                "        \"bms.resv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.frontPipeTempErrCnt\": 0,\n" +
                "        \"pd.highWindSpeedCnt\": 425,\n" +
                "        \"power.acCurrRms\": 5870,\n" +
                "        \"motor.fourWayState\": 0,\n" +
                "        \"pd.psdrPower\": 0,\n" +
                "        \"pd.errMotorCommCnt\": 0,\n" +
                "        \"pd.timeSet\": 0,\n" +
                "        \"pd.dpPowerSupplyTime\": 0,\n" +
                "        \"power.llcCurr\": -24.8,\n" +
                "        \"pd.timeRemain\": 0,\n" +
                "        \"power.busVolt\": 4200,\n" +
                "        \"power.rlySts\": 2,\n" +
                "        \"pd.retTempRate\": 9.1,\n" +
                "        \"pd.wteFthEn\": 3,\n" +
                "        \"bms.bmsVol\": 0,\n" +
                "        \"power.acWattsRange2Time\": 246,\n" +
                "        \"pd.hotMaxCnt\": 38,\n" +
                "        \"bms.bmsReqCur\": 0,\n" +
                "        \"pd.powerSrc\": 1,\n" +
                "        \"power.tempNtc\": 49,\n" +
                "        \"power.tempMax\": 0,\n" +
                "        \"motor.commcAck\": 0,\n" +
                "        \"pd.evaTube\": 1.2,\n" +
                "        \"pd.batPowerSupplyCnt\": 0,\n" +
                "        \"pd.errWifiCommCnt\": 0,\n" +
                "        \"motor.backFanBlockCnt\": 0,\n" +
                "        \"motor.setWaterRpm\": 0,\n" +
                "        \"motor.errCode\": 0,\n" +
                "        \"pd.fanSts\": 25,\n" +
                "        \"pd.windCnt\": 455,\n" +
                "        \"pd.sysPowerWatts\": 0,\n" +
                "        \"motor.hotEcoTime\": 0,\n" +
                "        \"power.tempMin\": 0,\n" +
                "        \"bms.bmsChgTime\": 0,\n" +
                "        \"motor.hotMaxTime\": 0,\n" +
                "        \"pd.rgbState\": 1,\n" +
                "        \"pd.resv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.frontOutTempErrCnt\": 0,\n" +
                "        \"pd.powerSts\": 1,\n" +
                "        \"power.pvPowerSupplyCnt\": 0,\n" +
                "        \"motor.backFanWorkTime\": 25877,\n" +
                "        \"pd.evapTemp\": 1967,\n" +
                "        \"bms.powerOffCnt\": 65535,\n" +
                "        \"power.mpptVol\": 240,\n" +
                "        \"power.mpptWattsRange2Time\": 0,\n" +
                "        \"pd.evaRet\": -6.2,\n" +
                "        \"motor.condeFanRpm\": 0,\n" +
                "        \"bms.bmsMinDsgSoc\": 0,\n" +
                "        \"pd.batDsgRemain\": 0,\n" +
                "        \"motor.setCompressorRpm\": 0,\n" +
                "        \"pd.batPower\": 0,\n" +
                "        \"pd.motorOutTemp\": 3604,\n" +
                "        \"motor.resv\": [\n" +
                "            3,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.recv\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"power.llcOcpInt\": 0,\n" +
                "        \"pd.reserved\": [\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0,\n" +
                "            0\n" +
                "        ],\n" +
                "        \"pd.pdErrCode\": 0,\n" +
                "        \"pd.coolSleepCnt\": 33,\n" +
                "        \"pd.refPowerWatts\": 0,\n" +
                "        \"power.psdrCnt\": 39422,\n" +
                "        \"pd.frontBarTempErrCnt\": 0,\n" +
                "        \"motor.waterPumpWorkTime\": 2334,\n" +
                "        \"motor.serveCtrlErr\": 0,\n" +
                "        \"pd.psdrCnt\": 54510,\n" +
                "        \"pd.airInTemp\": 2257,\n" +
                "        \"pd.setTempfah\": 75,\n" +
                "        \"motor.coolEcoTime\": 46,\n" +
                "        \"motor.v24\": 6140,\n" +
                "        \"power.mpptPwr\": 0,\n" +
                "        \"bms.bmsMaxChgSoc\": 0,\n" +
                "        \"pd.llcCurr\": -24.8,\n" +
                "        \"motor.coolNormalTime\": 858,\n" +
                "        \"pd.errCode\": 0,\n" +
                "        \"pd.lcdStatus\": 1,\n" +
                "        \"power.mpptVolRange4Time\": 0,\n" +
                "        \"pd.coolEcoCnt\": 37,\n" +
                "        \"pd.dmPowerSupplyTime\": 0,\n" +
                "        \"bms.bmsChgDsgSts\": 0,\n" +
                "        \"pd.subMode\": 3,\n" +
                "        \"power.acWattsRange3Time\": 0\n" +
                "    }";
        Map<Object, Object> dataMap = new Gson().fromJson(value, Map.class);




        String[] head = {"20-1quota","int"};
        List<String[]> list = new ArrayList<>();
        list.add(head);


        for(Object key: dataMap.keySet()) {
            String[] content = {"",""};
            content[0] = key.toString();
            content[1] = "int";
            list.add(content);
        }

        CSVWriter csvWriter = new CSVWriter(new FileWriter("D:\\test\\kt305quota.csv"));
        csvWriter.writeAll(list);
        csvWriter.close();

        System.out.println(dataMap);


    }
}
