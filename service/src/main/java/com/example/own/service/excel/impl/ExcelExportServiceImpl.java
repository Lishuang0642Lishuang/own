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




        String p = "ds.sdf";
        String[] array = p.split(".");
        System.out.println(array);


        String value = "{\"code\":\"0\",\"message\":\"Success\",\"data\":{\"bms_bmsStatus.hwVersion\":[86,48,46,49,46,50],\"mppt.faultCode\":0,\"mppt.dc24vState\":0,\"pd.wattsInSum\":0,\"bms_emsStatus.maxChargeSoc\":91,\"pd.wifiVer\":0,\"inv.cfgAcWorkMode\":0,\"bms_bmsStatus.designCap\":40000,\"bms_bmsStatus.temp\":23,\"bms_bmsStatus.f32ShowSoc\":38.3,\"pd.bpPowerSoc\":91,\"bms_bmsStatus.outputWatts\":0,\"pd.beepMode\":0,\"mppt.pv2ChgPauseFlag\":0,\"pd.typec2Watts\":0,\"inv.outputWatts\":0,\"bms_bmsStatus.vol\":53044,\"pd.pv1ChargeWatts\":0,\"inv.invOutFreq\":0,\"mppt.inAmp\":0,\"bms_emsStatus.bmsIsConnt\":[3,0,0],\"bms_bmsStatus.maxVolDiff\":20,\"mppt.pv2ChgState\":0,\"pd.XT150Watts1\":0,\"bms_emsStatus.dsgCmd\":2,\"bms_bmsStatus.fullCap\":39280,\"mppt.inVol\":24,\"pd.XT150Watts2\":0,\"bms_emsStatus.chgVol\":55044,\"bms_bmsStatus.balanceState\":0,\"pd.icoBytes\":[0,0,128,0,128,0,0,0,0,0,0,0,0,0],\"inv.fanState\":0,\"inv.acChgRatedPower\":2400,\"pd.usb1Watts\":0,\"inv.cfgAcXboost\":1,\"inv.outTemp\":23,\"mppt.res\":[0,0,0,0],\"mppt.dcdc12vAmp\":0,\"inv.invOutVol\":0,\"bms_bmsStatus.errCode\":24,\"mppt.pv2MpptTemp\":22,\"bms_emsStatus.chgAmp\":60000,\"inv.inputWatts\":0,\"bms_emsStatus.chgState\":1,\"bms_bmsStatus.inputWatts\":0,\"mppt.pv2CfgChgType\":1,\"pd.watchIsConfig\":0,\"bms_emsStatus.openBmsIdx\":1,\"pd.typec2Temp\":23,\"pd.carUsedTime\":40770,\"pd.typec1Watts\":0,\"pd.chgDsgState\":1,\"inv.chgPauseFlag\":0,\"inv.acInFreq\":0,\"mppt.carStandbyMin\":306,\"pd.pv2ChargeType\":0,\"pd.otherKitState\":0,\"pd.soc\":38,\"inv.invOutAmp\":0,\"bms_emsStatus.fanLevel\":0,\"mppt.carOutVol\":0,\"inv.standbyMin\":0,\"mppt.pv2ChgType\":0,\"inv.dcInVol\":0,\"pd.acAutoPause\":0,\"pd.dsgPowerDC\":149,\"inv.SlowChgWatts\":600,\"inv.dcInAmp\":0,\"mppt.carOutAmp\":27,\"pd.typecUsedTime\":1400,\"inv.prBalanceMode\":0,\"bms_bmsStatus.remainCap\":15057,\"pd.brightLevel\":3,\"inv.dcInTemp\":23,\"mppt.pv2InVol\":20,\"pd.acAutoOnCfg\":0,\"bms_emsStatus.maxAvailNum\":0,\"mppt.dcdc12vVol\":0,\"pd.newAcAutoOnCfg\":1,\"bms_emsStatus.maxCloseOilEb\":108,\"pd.minAcSoc\":21,\"pd.wattsOutSum\":0,\"mppt.carOutWatts\":0,\"pd.relaySwitchCnt\":0,\"bms_bmsStatus.recv\":[0,0,0,0,0,0,0,0,0,0],\"mppt.outVol\":6471,\"bms_emsStatus.f32LcdShowSoc\":38.3,\"inv.cfgAcEnabled\":0,\"bms_emsStatus.paraVolMax\":54045,\"mppt.chgType\":0,\"pd.pv1ChargeType\":0,\"bms_bmsStatus.bmsFault\":0,\"inv.acDipSwitch\":1,\"mppt.cfgChgType\":1,\"inv.acPassbyAutoEn\":0,\"bms_emsStatus.paraVolMin\":52045,\"inv.dischargeType\":0,\"mppt.chgState\":0,\"pd.carState\":0,\"pd.invUsedTime\":228243,\"bms_emsStatus.bmsWarState\":0,\"mppt.carState\":0,\"pd.typec1Temp\":23,\"bms_bmsStatus.sysVer\":33620236,\"pd.dcInUsedTime\":23201,\"mppt.carTemp\":22,\"pd.model\":1,\"bms_bmsStatus.minCellVol\":3307,\"bms_bmsStatus.cycles\":6,\"mppt.outWatts\":0,\"pd.wifiAutoRcvy\":0,\"pd.remainTime\":5999,\"bms_bmsStatus.maxCellVol\":3327,\"bms_bmsStatus.type\":1,\"bms_bmsStatus.maxCellTemp\":24,\"mppt.outAmp\":143,\"bms_bmsStatus.cellId\":2,\"bms_bmsStatus.cellTemp\":[],\"bms_bmsStatus.minMosTemp\":23,\"mppt.chgPauseFlag\":0,\"bms_emsStatus.minOpenOilEb\":103,\"pd.hysteresisAdd\":5,\"inv.chargerType\":0,\"pd.chgSunPower\":0,\"pd.carTemp\":22,\"bms_bmsStatus.cellVol\":[3319,3307],\"bms_bmsStatus.bqSysStatReg\":0,\"inv.acInAmp\":0,\"pd.pvChargePrioSet\":0,\"bms_emsStatus.chgRemainTime\":5999,\"bms_bmsStatus.openBmsIdx\":1,\"pd.dsgPowerAC\":6140,\"pd.qcUsb2Watts\":0,\"bms_bmsStatus.num\":0,\"pd.wireWatts\":0,\"bms_bmsStatus.mosState\":1,\"mppt.pv2InAmp\":0,\"pd.chgPowerAC\":5836,\"pd.lcdOffSec\":12,\"bms_bmsStatus.soc\":38,\"inv.acInVol\":0,\"inv.FastChgWatts\":2400,\"mppt.pv2Xt60ChgType\":0,\"pd.sysVer\":16975436,\"mppt.pv2DcChgCurrent\":4000,\"mppt.dc24vTemp\":22,\"pd.invInWatts\":0,\"bms_emsStatus.chgCmd\":1,\"bms_bmsStatus.tagChgAmp\":40000,\"bms_bmsStatus.maxMosTemp\":25,\"pd.qcUsb1Watts\":0,\"pd.reserved\":[0,0],\"mppt.pv2InWatts\":0,\"bms_bmsStatus.minCellTemp\":23,\"pd.chgPowerDC\":5174,\"mppt.swVer\":83886156,\"pd.standbyMin\":240,\"mppt.x60ChgType\":0,\"inv.cfgAcOutFreq\":1,\"bms_bmsStatus.soh\":100,\"inv.errCode\":0,\"bms_emsStatus.openUpsFlag\":1,\"mppt.dcdc12vWatts\":0,\"bms_emsStatus.minDsgSoc\":90,\"pd.usbqcUsedTime\":30348,\"bms_bmsStatus.remainTime\":0,\"pd.dcOutState\":0,\"mppt.inWatts\":0,\"pd.bmsKitState\":[0,0],\"bms_emsStatus.emsIsNormalFlag\":1,\"pd.usbUsedTime\":4308,\"pd.mpptUsedTime\":0,\"inv.reserved\":[0,0,0,0,0,0],\"mppt.mpptTemp\":23,\"pd.wifiRssi\":0,\"bms_bmsStatus.amp\":-57,\"inv.invType\":8,\"bms_emsStatus.lcdShowSoc\":38,\"inv.cfgAcOutVol\":230000,\"bms_emsStatus.bmsModel\":1,\"pd.errCode\":0,\"pd.pv2ChargeWatts\":0,\"pd.carWatts\":0,\"pd.usb2Watts\":0,\"mppt.dcChgCurrent\":4000,\"pd.invOutWatts\":0,\"bms_emsStatus.dsgRemainTime\":5999,\"inv.sysVer\":33554497},\"tid\":\"\"}";
        Map<Object, Object> map = new Gson().fromJson(value, Map.class);

        Map dataMap = (Map)map.get("data");



        String[] head = {"quota","dd"};
        List<String[]> list = new ArrayList<>();
        list.add(head);


        for(Object key: dataMap.keySet()) {
            String[] content = {"",""};
            content[0] = key.toString();
            content[1] = "dd";
            list.add(content);
        }

        CSVWriter csvWriter = new CSVWriter(new FileWriter("D:\\test\\quota.csv"));
        csvWriter.writeAll(list);
        csvWriter.close();

        System.out.println(map);


    }
}
