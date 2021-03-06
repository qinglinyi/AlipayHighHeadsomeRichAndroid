package im.hoho.alipayInstallB;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by qzj_ on 2016/5/9.
 */
public class PluginMain implements IXposedHookLoadPackage {

    public PluginMain() {
        XposedBridge.log("Now Loading HOHO`` alipay plugin...");
    }


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        //Anti hook checker
//        XposedHelpers.findAndHookMethod(
//                "java.lang.Throwable",
//                lpparam.classLoader,
//                "getStackTrace",
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param)
//                            throws Throwable {
//                        StackTraceElement[] result = (StackTraceElement[]) param.getResult();
//                        boolean xposedDetected = false;
//                        for (StackTraceElement localStackTraceElement : result) {
//                            if (localStackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")) {
//                                //found xposed
//                                XposedBridge.log("Now, let's skip the Alipay hook checker, HOHO`` Anti Hook Starting.., skip method name: " + localStackTraceElement.getMethodName());
//                                xposedDetected = true;
//                                break;
//                            }
//                        }
//                        if (xposedDetected) {
//                            //Replaces the returning value
//                            List<StackTraceElement> newResult = new ArrayList<>();
//                            for (StackTraceElement localStackTraceElement : result) {
//                                if (localStackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")) {
//                                    //found xposed and skip...
//                                    continue;
//                                }
//                                newResult.add(localStackTraceElement);
//                            }
//                            StackTraceElement[] retval = newResult.toArray(new StackTraceElement[newResult.size()]);
//                            XposedBridge.log("Anti Hook returning...");
//                            param.setResult(retval);
//                        }
//                    }
//                });

//
        if (lpparam.packageName.contains("com.eg.android.AlipayGphone")) {
            XposedBridge.log("Loaded App: " + lpparam.packageName);
            XposedBridge.log("Powered by HOHO`` 20170821");

//            //StackTraceElement[] getStackTrace
////            throw Exception
//
//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.edge.impl.EdgeRiskServiceImpl",
//                    lpparam.classLoader,
//                    "getRiskResult",
//                    String.class,
//                    Map.class,
//                    int.class,
//
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("Now, let's skip the Alipay hook checker, HOHO`` Anti Hook Starting...");
//                            return null;
//                        }
//                    });
//
//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.edge.pipeline.a",
//                    lpparam.classLoader,
//                    "run",
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("Now, let's skip the Alipay hook checker, HOHO`` Anti Hook Starting...");
//                            return null;
//                        }
//                    });


//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.edge.impl.a",
//                    lpparam.classLoader,
//                    "a",
//                    byte[].class,
//                    new XC_MethodReplacement() {
//                        @Override
//                        protected Object replaceHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("Now, let's skip the Alipay hook checker, HOHO`` Anti Hook Starting...");
//                            return null;
//                        }
//                    });

//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.edge.utils.FindHook",
//                    lpparam.classLoader,
//                    "a",
//                    Context.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("Now, let's skip the Alipay hook checker, HOHO`` Anti Hook Starting...");
//                            param.setResult(false);
//                        }
//                    });

            //Pay Diamond
            XposedHelpers.findAndHookMethod(
                    "com.alipay.mobilegw.biz.shared.processer.login.UserLoginResult",
                    lpparam.classLoader,
                    "getExtResAttrs",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            XposedBridge.log("Now, let's install B...");
                            XSharedPreferences pre = new XSharedPreferences("im.hoho.alipayInstallB", "prefs");
                            pre.makeWorldReadable();
                            String enabledBackground = pre.getString("enableBackground", "true");
                            XposedBridge.log("enableBackground value: " + enabledBackground);

                            if (!enabledBackground.equals("true")) {
                                XposedBridge.log("Install B is failed! Not enabled.");
                                return;
                            }

                            Map<String, String> result = (Map<String, String>) param.getResult();
                            if (result.containsKey("memberGrade")) {
                                XposedBridge.log("Original member grade: " + result.get("memberGrade"));
                                XposedBridge.log("Putting \"diamond\" into dict...");
                                result.put("memberGrade", "diamond");
                                XposedBridge.log("Member grade changed to: " + result.get("memberGrade"));
                            } else {
                                XposedBridge.log("Can not get the member grade in return value...WTF?");
                            }
                        }
                    });

            //UserInfo memberInfo
            XposedHelpers.findAndHookMethod(
                    "com.alipay.mobile.framework.service.ext.security.bean.UserInfo",
                    lpparam.classLoader,
                    "getWalletEdition",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param)
                                throws Throwable {
                            XposedBridge.log("walletEdition: " + param.getResult());
                        }
                    });

//            //download url
//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.mobile.framework.service.ext.openplatform.domain.AppEntity",
//                    lpparam.classLoader,
//                    "setDownloadUrl",
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("setDownloadUrl: " + param.args[0]);
//                        }
//                    });

//            //setPageUrl
//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.mobile.framework.service.ext.openplatform.domain.AppEntity",
//                    lpparam.classLoader,
//                    "setPageUrl",
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("setPageUrl: " + param.args[0]);
//                        }
//                    });

//            //feeAmount
//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.wealth.common.ui.PopupFloatView",
//                    lpparam.classLoader,
//                    "setFeeAmount",
//                    String.class,
//                    String.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("setFeeAmount 1st: " + param.args[0]);
//                            XposedBridge.log("setFeeAmount 2st: " + param.args[1]);
//                        }
//                    });

//            //feeAmount2
//            XposedHelpers.findAndHookMethod(
//                    "com.alipay.wealth.common.ui.PopupFloatView",
//                    lpparam.classLoader,
//                    "setFeeAmountValue",
//                    String.class,
//                    boolean.class,
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param)
//                                throws Throwable {
//                            XposedBridge.log("setFeeAmountValue 1st: " + param.args[0]);
//                            XposedBridge.log("setFeeAmountValue 2st: " + param.args[1].toString());
//                        }
//                    });


            //Yu'ebao
            final Class<?> FundHomeInfoV99ResultPB = lpparam.classLoader.loadClass("com.alipay.mobilewealth.biz.service.gw.result.mfund.pb.FundHomeInfoV99ResultPB");
            XposedHelpers.findAndHookMethod(
                    "com.alipay.mobile.fund.ui.FundMainNewActivity",
                    lpparam.classLoader,
                    "a",
                    FundHomeInfoV99ResultPB,
                    boolean.class,
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            XposedBridge.log("Let's do it...Changing me to GaoShuaiFu!");
                            XSharedPreferences pre = new XSharedPreferences("im.hoho.alipayInstallB", "prefs");
                            pre.makeWorldReadable();
                            String increaseString = pre.getString("yuebaoIncreaseAmount", "12345678");
                            String totalProfitSetString = pre.getString("yuebaoTotalProfit", "123456");
                            String enableYuEBao = pre.getString("enableYuEBao", "true");

                            XposedBridge.log("enableYuEBao value: " + enableYuEBao);
                            if (!enableYuEBao.equals("true")) {
                                XposedBridge.log("Not enabled, keep original...");
                                return;
                            }

                            boolean canSetPreProfit = false;
                            Field previousProfitField = FundHomeInfoV99ResultPB.getDeclaredField("previousProfit");
                            Field totalAmountField = FundHomeInfoV99ResultPB.getDeclaredField("totalAmount");
                            Field weekRateField = FundHomeInfoV99ResultPB.getDeclaredField("weekRate");
                            Field totalProfitField = FundHomeInfoV99ResultPB.getDeclaredField("totalProfit");


                            String previousProfit = (String) previousProfitField.get(param.args[0]);//最后收益
                            String totalAmount = (String) totalAmountField.get(param.args[0]);//总金额



                            if (previousProfit.contains("."))
                                canSetPreProfit = true;
                            XposedBridge.log("previousProfit: " + previousProfit);

                            BigDecimal previousProfitProcessing = canSetPreProfit ? new BigDecimal(previousProfit) : BigDecimal.ZERO;
                            canSetPreProfit = true;//Force profit sets.
                            XposedBridge.log("previousProfitProcessing: " + previousProfitProcessing.toString());
                            if (previousProfitProcessing.compareTo(BigDecimal.ZERO) <= 0) {
                                XposedBridge.log("So poor... earned no money in yu'e bao. Now let's calc the previousProfit.");
                                BigDecimal weekRate;
                                try {
                                    weekRate = new BigDecimal((String) weekRateField.get(param.args[0]));
                                } catch (Exception ex) {
                                    XposedBridge.log("Failed to get the week rate, no digits will be changed.");
                                    return;
                                }
                                if (weekRate.compareTo(BigDecimal.ZERO) <= 0) {
                                    XposedBridge.log("Week rate is 0.WTF?");
                                    return;
                                }
                                XposedBridge.log("Week rate is " + weekRate.toString());

                                XposedBridge.log("I'm ready, this v1.0.8 version will calc the previousProfit for you...");
                                previousProfit = "0.01";//assume the previous profit is 0.01
                                previousProfitProcessing= BigDecimal.valueOf(0.01);
                                totalAmount = previousProfitProcessing.multiply(BigDecimal.valueOf(365)).divide(weekRate,20, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)).toString();
                                XposedBridge.log("Calcing is finished, totalAmount will be override to: " + totalAmount);
                            }

                            BigDecimal totalAmountProcessing = new BigDecimal(totalAmount);

                            if (!increaseString.equals("1130")) {
                                BigDecimal increaseTotal = new BigDecimal(increaseString);
                                BigDecimal totalProfitDec = new BigDecimal(totalProfitSetString).setScale(2, RoundingMode.HALF_EVEN);

                                if (totalAmountProcessing.compareTo(increaseTotal) > 0) {
                                    XposedBridge.log("Your total amount larger than the increasing value, now decreasing to increased value....");
                                    return;
                                }


                                BigDecimal profitRatio = totalAmountProcessing.divide(previousProfitProcessing, 30, RoundingMode.HALF_EVEN);
                                totalAmountProcessing = totalAmountProcessing.add(increaseTotal).setScale(2, RoundingMode.HALF_EVEN);
                                XposedBridge.log("profitRatio: " + profitRatio.toString());
                                previousProfitProcessing = totalAmountProcessing.divide(profitRatio, 2, RoundingMode.HALF_EVEN);

                                XposedBridge.log("totalProfit before: " + (String) totalProfitField.get(param.args[0]));
                                XposedBridge.log("previousProfit before: " + previousProfit);
                                XposedBridge.log("totalAmount before: " + totalAmount);
                                XposedBridge.log("totalProfit settings value: " + totalProfitSetString);

                                totalProfitField.set(param.args[0], totalProfitDec.toString());
                                if (canSetPreProfit)
                                    previousProfitField.set(param.args[0], previousProfitProcessing.toPlainString());
                                totalAmountField.set(param.args[0], totalAmountProcessing.toPlainString());

                                XposedBridge.log("previousProfit after: " + (String) previousProfitField.get(param.args[0]));
                                XposedBridge.log("totalAmount after: " + (String) totalAmountField.get(param.args[0]));
                                XposedBridge.log("totalProfit after: " + (String) totalProfitField.get(param.args[0]));
                            } else {
                                previousProfitField.set(param.args[0], "神！");
                                totalAmountField.set(param.args[0], "高端大气上档次");
                                weekRateField.set(param.args[0], "狂拽酷炫吊炸天");
                            }
                        }
                    });

            XposedBridge.log("Hook function was executed.");
        }
    }
}
