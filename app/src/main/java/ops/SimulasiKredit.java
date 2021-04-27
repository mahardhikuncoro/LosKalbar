package ops;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.List;

import base.sqlite.IndicatorHarga;
import base.sqlite.IndicatorHargaModel;
import base.sqlite.IndicatorTahun;
import base.sqlite.IndicatorTahunModel;
import base.sqlite.ParamKategoriAsuransi;
import base.sqlite.ParamKategoriAsuransiModel;
import base.sqlite.ParamPenyusutan;
import base.sqlite.ParamPenyusutanModel;
import base.sqlite.ParamPinjamanExtended;
import base.sqlite.ParamPinjamanExtendedModel;
import base.sqlite.ParameterPinjaman;
import base.sqlite.ParameterPinjamanModel;
import base.sqlite.ParameterTenor;
import base.sqlite.ParameterTenorModel;
import base.sqlite.RateAsuransi;
import base.sqlite.RateAsuransiJiwa;
import base.sqlite.RateAsuransiJiwaModel;
import base.sqlite.RateAsuransiModel;
import base.sqlite.RateAsuransiMotor;
import base.sqlite.RateAsuransiMotorModel;
import base.sqlite.RateBiaya;
import base.sqlite.RateBiayaModel;
import base.sqlite.RateBunga;
import base.sqlite.RateBungaModel;
import id.co.smmf.mobile.BuildConfig;
import ops.screen.KreditModel;

/**
 * Created by christian on 4/9/18.
 */

public class SimulasiKredit {

    public static KreditModel getParameter(Context context, KreditModel kreditModel) {
        IndicatorTahun indicatorTahun = new IndicatorTahun(context);
        IndicatorHarga indicatorHarga = new IndicatorHarga(context);
        ParameterPinjaman parameterPinjaman = new ParameterPinjaman(context);
        ParamKategoriAsuransi paramKategoriAsuransi = new ParamKategoriAsuransi(context);
        ParameterTenor parameterTenor = new ParameterTenor(context);
        ParamPenyusutan paramPenyusutan = new ParamPenyusutan(context);
        RateAsuransi rateAsuransi = new RateAsuransi(context);
        RateAsuransiMotor rateAsuransiMotor = new RateAsuransiMotor(context);
        RateAsuransiJiwa rateAsuransiJiwa = new RateAsuransiJiwa(context);
        RateBiaya rateBiaya = new RateBiaya(context);
        RateBunga rateBunga = new RateBunga(context);
        ParamPinjamanExtended paramPinjamanExtended = new ParamPinjamanExtended(context);

        IndicatorTahunModel indicatorTahunModel;
        IndicatorHargaModel indicatorHargaModel;
        RateBungaModel rateBungaModel;
        RateAsuransiModel rateAsuransiModel;
        RateAsuransiMotorModel rateAsuransiMotorModel;
        RateBiayaModel rateBiayaModel;
        ParamKategoriAsuransiModel paramKategoriAsuransiModel;
        ParameterTenorModel parameterTenorModel;
        ParamPenyusutanModel paramPenyusutanModel;
        RateAsuransiJiwaModel rateAsuransiJiwaModel;
        ParameterPinjamanModel parameterPinjamanModel;
        ParamPinjamanExtendedModel paramPinjamanExtendedModel;

//        Log.e("cek Harga", kreditModel.getDana().toString());
//        Log.e("cek Dp", kreditModel.getUangMuka().toString());
//        Log.e("cek Produk", kreditModel.getProduk());
//        Log.e("cek Paket", kreditModel.getPaket());
//        Log.e("cek Tenor", kreditModel.getTenor().toString());

        //PerhituganPHMobil
        if (kreditModel.getDana() != null && kreditModel.getUangMuka() != null &&
                kreditModel.getProduk() != null) {
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
//                Log.e("DP ", "mobil " + kreditModel.getUangMuka().toString());
                Double ph = kreditModel.getDana() - kreditModel.getUangMuka();
//                Log.e("PH murni", "mobil" + String.valueOf(ph));
                kreditModel.setPokokHutang(ph);
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
//                Log.e("PH murni", "motor");
                Double ph = kreditModel.getDana() - kreditModel.getUangMuka();
                kreditModel.setPokokHutangMotor(ph);
//                Log.e("PH Motor", kreditModel.getPokokHutangMotor().toString());
            }
        }


        /*//PerhitunganOtr
        if (kreditModel.getDana()!=null){
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                parameterPinjamanModel = parameterPinjaman.selectBy("pokok_hutang");
                Integer valueMobil = parameterPinjamanModel.getValue_mobil();
                Double hitungOtr = kreditModel.getPokokHutang() / valueMobil *100;
                kreditModel.setOtr(hitungOtr);
//                Log.e("OTR", kreditModel.getOtr().toString());
            }
        }*/

        //PerhitunganOtr
        if (kreditModel.getDana()!=null){
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
//                Log.e("max dp mobil", kreditModel.getMaxDpMobil().toString());
                Double hitungOtr = kreditModel.getPokokHutang() / (100 - (kreditModel.getMaxDpMobil())) *100;
                kreditModel.setOtr(hitungOtr);
//                Log.e("OTR", kreditModel.getOtr().toString());
            }
        }


        //penyusutan
        if (kreditModel.getTenor() != null && kreditModel.getProduk() != null) {
//            Log.e("Penyusutan", "ini");
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                paramPenyusutanModel = paramPenyusutan.selectBy(kreditModel.getTenor() / 12);
                kreditModel.setPenyusutan(paramPenyusutanModel.getValue());
//                Log.e("penyusutan", kreditModel.getPenyusutan().toString());
            }

        }

        //hitungOTRPenyusutan
        if (kreditModel.getOtr() != null && kreditModel.getPenyusutan() != null) {
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                Double otrPenyusutan = kreditModel.getOtr() * kreditModel.getPenyusutan()/100;
                kreditModel.setOtrPenyusutan(otrPenyusutan);
//            Log.e("OTR penyusutan", kreditModel.getOtrPenyusutan().toString());
            }
        }

//        indicatorHarga
        if (kreditModel.getOtrPenyusutan() != null) {
//            Log.e("Indicator harga", "ini");
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
//                Log.e("OTR Penyusutan", kreditModel.getOtrPenyusutan().toString());
                indicatorHargaModel = indicatorHarga.selectBetween(kreditModel.getOtrPenyusutan());
//                Log.e("Batas atas", indicatorHargaModel.getBatasAtas().toString());
//                Log.e("Batas bawah", indicatorHargaModel.getBatasBawah().toString());
                kreditModel.setIndicatorHarga(indicatorHargaModel.getIndicator_harga());
//                Log.e("iHarga", indicatorHargaModel.getIndicator_harga());
            }
        }

        //indicatorTahun
        if (kreditModel.getUsiaKendaraan() != null) {
//            Log.e("Indicator tahun", "ini");
            indicatorTahunModel = indicatorTahun.selectBetween(kreditModel.getUsiaKendaraan());
            kreditModel.setIndicatorTahun(indicatorTahunModel.getIndicator_tahun());
//            Log.e("iTahun", indicatorTahunModel.getIndicator_tahun());
        }

        //kategoriAsuransi
       /* if(BuildConfig.FLAVOR.equalsIgnoreCase("ku")) {
            if (kreditModel.getUsiaKendaraan() != null || kreditModel.getTenor() != null) {
//            Log.e("Usia Kendaraan", "ini");
                if (kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_3") ||
                        kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_4") ||
                        kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_5")) {
                    if (kreditModel.getTenor() == 12) {
                        kreditModel.setKategoriAsuransi("OVER_TENOR_1_TAHUN");
                    } else if (kreditModel.getTenor() == 24) {
                        kreditModel.setKategoriAsuransi("OVER_TENOR_2_TAHUN");
                    } else if (kreditModel.getTenor() == 36) {
                        kreditModel.setKategoriAsuransi("OVER_TENOR_3_TAHUN");
                    }

                } else if (kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_1") ||
                        kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_2")) {
                    if (kreditModel.getTenor() == 12) {
                        paramKategoriAsuransiModel = paramKategoriAsuransi.selectBetween(kreditModel.getUsiaKendaraan());
                        kreditModel.setKategoriAsuransi(paramKategoriAsuransiModel.getValue());
//            Log.e("iKatAsuransi", kreditModel.getKategoriAsuransi());
                    } else {
                        kreditModel.setKategoriAsuransi("TLO");
                    }
                }
            }
        }else{*/
            if (kreditModel.getUsiaKendaraan() != null || kreditModel.getTenor() != null) {
//            Log.e("Usia Kendaraan", "ini");
                if (kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_3") ||
                        kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_4")) {
                    if (kreditModel.getTenor() == 12) {
                        kreditModel.setKategoriAsuransi("OVER_TENOR_1_TAHUN");
                    } else if (kreditModel.getTenor() == 24) {
                        kreditModel.setKategoriAsuransi("OVER_TENOR_2_TAHUN");
                    } else if (kreditModel.getTenor() == 36) {
                        kreditModel.setKategoriAsuransi("OVER_TENOR_3_TAHUN");
                    }

                } else if (kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_1") ||
                        kreditModel.getIndicatorTahun().equalsIgnoreCase("kategori_tahun_2")) {
                    if (kreditModel.getTenor() == 12) {
                        paramKategoriAsuransiModel = paramKategoriAsuransi.selectBetween(kreditModel.getUsiaKendaraan());
                        kreditModel.setKategoriAsuransi(paramKategoriAsuransiModel.getValue());
//            Log.e("iKatAsuransi", kreditModel.getKategoriAsuransi());
                    } else {
                        kreditModel.setKategoriAsuransi("TLO");
                    }
                }
            }
//        }

        //maxTenor
        /*if (kreditModel.getProduk() != null && kreditModel.getUsiaKendaraan() != null) {
//            Log.e("Max Tenor", "ini");
            if (kreditModel.getUsiaKendaraan() <=15) {
                parameterTenorModel = parameterTenor.selectBetween(kreditModel.getProduk(), kreditModel.getUsiaKendaraan());
                kreditModel.setMaxTenor(parameterTenorModel.getValue());
//            Log.e("iMaxTenor", kreditModel.getMaxTenor().toString());
            } else{
                kreditModel.setMaxTenor(48);
            }
        }*/


        //rateBunga
        if (kreditModel.getProduk() != null && kreditModel.getPaket() != null &&
                kreditModel.getIndicatorTahun() != null) {
//            Log.e("Rate Bunga", "ini");
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
//                Log.e("rate bunga", "mobil");
                rateBungaModel = rateBunga.selectBy(kreditModel.getPaket(), kreditModel.getProduk(),
                        kreditModel.getIndicatorTahun());
                if (kreditModel.getTenor() == 12) {
                    kreditModel.setRateBunga(rateBungaModel.getRateTahun1());
                } else if (kreditModel.getTenor() == 24) {
                    kreditModel.setRateBunga(rateBungaModel.getRateTahun2());
                } else if (kreditModel.getTenor() == 36) {
                    kreditModel.setRateBunga(rateBungaModel.getRateTahun3());
                } else if (kreditModel.getTenor() == 48) {
                    kreditModel.setRateBunga(rateBungaModel.getRateTahun4());
                }/* else {
                    kreditModel.setRateBunga(0D);
                }*/
//            Log.e("iRateBunga", kreditModel.getRateBunga().toString());
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
//                Log.e("rate bunga", "motor");
                rateBungaModel = rateBunga.selectBy(kreditModel.getPaket(), kreditModel.getProduk(),
                        "kategori_motor");
                if (kreditModel.getTenor() == 6) {
                    kreditModel.setRateBungaMotor(rateBungaModel.getRateTahun1());
                } else if (kreditModel.getTenor() == 12) {
                    kreditModel.setRateBungaMotor(rateBungaModel.getRateTahun2());
                } else if (kreditModel.getTenor() == 18) {
                    kreditModel.setRateBungaMotor(rateBungaModel.getRateTahun3());
                } else if (kreditModel.getTenor() == 24) {
                    kreditModel.setRateBungaMotor(rateBungaModel.getRateTahun4());
                }
//            Log.e("iRateBunga", kreditModel.getRateBungaMotor().toString());
            }

        }

        //rateAsuransi
        if (kreditModel.getPaket() != null && kreditModel.getIndicatorHarga() != null &&
                kreditModel.getKategoriAsuransi() != null && kreditModel.getPenyusutan() != null) {
//            Log.e("Rate Asuransi", "ini");

            if (kreditModel.getKategoriAsuransi().equalsIgnoreCase("OVER_TENOR_1_TAHUN") ||
                    kreditModel.getKategoriAsuransi().equalsIgnoreCase("OVER_TENOR_2_TAHUN") ||
                    kreditModel.getKategoriAsuransi().equalsIgnoreCase("OVER_TENOR_3_TAHUN")){
//                Log.e("Rate Asuransi", "OVER tenor");
                rateAsuransiModel = rateAsuransi.selectBy(kreditModel.getPaket(), kreditModel.getIndicatorHarga(),
                        kreditModel.getKategoriAsuransi());
                if (kreditModel.getWilayah() == 1) {
                    kreditModel.setRateAsuransiOver(rateAsuransiModel.getRateWilayah1());
                } else if (kreditModel.getWilayah() == 2) {
                    kreditModel.setRateAsuransiOver(rateAsuransiModel.getRateWilayah2());
                } else if (kreditModel.getWilayah() == 3) {
                    kreditModel.setRateAsuransiOver(rateAsuransiModel.getRateWilayah3());
                }
            } else {
//                Log.e("Rate Asuransi", "AllRisk atau TLO");
                rateAsuransiModel = rateAsuransi.selectBy(kreditModel.getPaket(), kreditModel.getIndicatorHarga(),
                        kreditModel.getKategoriAsuransi());
                if (kreditModel.getWilayah() == 1) {
                    kreditModel.setRateAsuransi(rateAsuransiModel.getRateWilayah1());
                } else if (kreditModel.getWilayah() == 2) {
                    kreditModel.setRateAsuransi(rateAsuransiModel.getRateWilayah2());
                } else if (kreditModel.getWilayah() == 3) {
                    kreditModel.setRateAsuransi(rateAsuransiModel.getRateWilayah3());
                }
            }
//            Log.e("iWilayah", kreditModel.getWilayah().toString());
//            Log.e("iRateAsuransi", kreditModel.getRateAsuransi().toString());
        }

        //rateAsuransiMotor
        if (kreditModel.getPaket() != null && kreditModel.getProduk() != null &&
                kreditModel.getTenor() != null) {
            rateAsuransiMotorModel = rateAsuransiMotor.selectBy(kreditModel.getProduk(),
                    kreditModel.getPaket(), kreditModel.getTenor());
            if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
                if (kreditModel.getTenor() == 6) {
                    kreditModel.setRateAsuransiMotor(rateAsuransiMotorModel.getValue_asuransi());
                } else if (kreditModel.getTenor() == 12) {
                    kreditModel.setRateAsuransiMotor(rateAsuransiMotorModel.getValue_asuransi());
                } else if (kreditModel.getTenor() == 18) {
                    kreditModel.setRateAsuransiMotor(rateAsuransiMotorModel.getValue_asuransi());
                } else if (kreditModel.getTenor() == 24) {
                    kreditModel.setRateAsuransiMotor(rateAsuransiMotorModel.getValue_asuransi());
                }
            }
//            Log.e("RateAsuransiMotor", kreditModel.getRateAsuransiMotor().toString());
        }

        //rateAsuransiJiwa
        if (kreditModel.getPokokHutang() != null) {
            rateAsuransiJiwaModel = rateAsuransiJiwa.selectBetween(kreditModel.getPokokHutang());
            if (kreditModel.getTenor() == 12) {
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_1());
            } else if (kreditModel.getTenor() == 24) {
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_2());
            } else if (kreditModel.getTenor() == 36) {
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_3());
            } else if (kreditModel.getTenor() == 48) {
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_4());
            }
//            Log.e("iRateASJ", kreditModel.getAsuransiJiwa().toString());
        }

        //PerhitunganBiaya
        if (kreditModel.getPaket() != null && kreditModel.getProduk() != null) {
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                List<RateBiayaModel> list = rateBiaya.selectBy(kreditModel.getPaket(), "ALL",
                        kreditModel.getProduk(), kreditModel.getTenor() / 12);
//                Log.e("size mobil", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getNamaBiaya().equalsIgnoreCase("survey")) {
                        kreditModel.setBiayaSurvey(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("administrasi")) {
                        kreditModel.setBiayaAdmin(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("fiducia")) {
                        kreditModel.setBiayaFiducia(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("provisi")) {
                        kreditModel.setRateProvisi(list.get(i).getValue());
                    }
                }

//            Log.e("iSurvey Mobil", kreditModel.getBiayaSurvey().toString());
//            Log.e("iAdmin Mobil", kreditModel.getBiayaAdmin().toString());
//            Log.e("iFiducia Mobil", kreditModel.getBiayaFiducia().toString());
//            Log.e("iRateProvisi Mobil", kreditModel.getRateProvisi().toString());//belum
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
                List<RateBiayaModel> list = rateBiaya.selectBy(kreditModel.getPaket(), "ALL",
                        kreditModel.getProduk(), kreditModel.getTenor());
//                Log.e("size motor", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getNamaBiaya().equalsIgnoreCase("survey")) {
                        kreditModel.setBiayaSurvey(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("administrasi")) {
                        kreditModel.setBiayaAdmin(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("fiducia")) {
                        kreditModel.setBiayaFiducia(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("provisi")) {
                        kreditModel.setRateProvisi(list.get(i).getValue());
                    }
                }

//                Log.e("iSurvey Motor", kreditModel.getBiayaSurvey().toString());
//                Log.e("iAdmin Motor", kreditModel.getBiayaAdmin().toString());
//                Log.e("iFiducia Motor", kreditModel.getBiayaFiducia().toString());
//                Log.e("iRateProvisi Motor", kreditModel.getRateProvisi().toString());
            }

//            Log.e("Biaya Admin", kreditModel.getBiayaAdmin().toString());
//            Log.e("Biaya Survey", kreditModel.getBiayaSurvey().toString());
//            Log.e("Biaya Fiducia", kreditModel.getBiayaFiducia().toString());
        }

        //PerhitunganAsuransiMobil
        if (kreditModel.getTenor() != null && kreditModel.getOtr() != null &&
                kreditModel.getTahunKendaraan() != null && kreditModel.getProduk() != null) {
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
//                Log.e("Nominal Asuransi", "ALLRISK atau Gabungan");
                if (kreditModel.getRateAsuransi()!=null) {
//                Log.e("Rate OTR ini", kreditModel.getOtrPenyusutan().toString());
//                Log.e("Rate Asuransi ini", kreditModel.getRateAsuransi().toString());
                    if (kreditModel.getNominalAsuransiMobil2() == null) {
//                    Log.e("nominal 2 ", "null");
                        Double nominalAsuransiMobil = (kreditModel.getRateAsuransi() * kreditModel.getOtrPenyusutan()) / 100;
                        kreditModel.setNominalAsuransiMobil(nominalAsuransiMobil);
                        kreditModel.setNominalAsuransiMobil2(nominalAsuransiMobil);
                    } else {
//                    Log.e("nominal 2 ", "ada");
                        Double nominalAsuransiMobil = (kreditModel.getRateAsuransi() * kreditModel.getOtrPenyusutan()) / 100;
//                    Log.e("nominal Asuransi", nominalAsuransiMobil.toString());
                        nominalAsuransiMobil = nominalAsuransiMobil + kreditModel.getNominalAsuransiMobil2();
                        kreditModel.setNominalAsuransiMobil(nominalAsuransiMobil);
                        kreditModel.setNominalAsuransiMobil2(nominalAsuransiMobil);
                    }
                } else if (kreditModel.getRateAsuransiOver()!=null){
//                    Log.e("Nominal Asuransi", "Over");
                    Double nominalAsuransiMobil = (kreditModel.getRateAsuransiOver() * kreditModel.getOtr()) / 100;
                    kreditModel.setNominalAsuransiMobil(nominalAsuransiMobil);
                }
//                Double nominalAsuransiMobil = (kreditModel.getRateAsuransi() * kreditModel.getOtrPenyusutan()) / 100;

//                Log.e("Asuransi Mobil", kreditModel.getNominalAsuransiMobil().toString());// belum harusnya 750000
            }
        }


        //PerhitunganAsuransiMotor
        if (kreditModel.getRateAsuransiMotor()!=null) {
            if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
//                Log.e("Asuransi", "Motor");
                Double asuransiMotor = kreditModel.getRateAsuransiMotor() * kreditModel.getDana() / 100;
                kreditModel.setNominalAsuransiMotor(asuransiMotor);
            }
//            Log.e("Final Asuransi", kreditModel.getNominalAsuransi().toString());
        }

        //PerhitunganPHMotor
        if (kreditModel.getDana() != null && kreditModel.getUangMuka() != null &&
                kreditModel.getProduk() != null) {
            if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
//                Log.e("PH terutang", "motor");
                Double phTerhutang = kreditModel.getPokokHutangMotor() + kreditModel.getBiayaAdmin() +
                        kreditModel.getBiayaSurvey() + kreditModel.getBiayaFiducia()
                        + kreditModel.getNominalAsuransiMotor();
                kreditModel.setPokokHutangTerhutang(phTerhutang);
            }
//            Log.e("Final PHMotor", kreditModel.getPokokHutangTerhutang().toString());
        }

        //PerhitunganBungaMobil
        if (kreditModel.getRateBunga() != null && kreditModel.getPokokHutang() != null &&
                kreditModel.getTenor() != null) {
            Double bunga = kreditModel.getRateBunga() * kreditModel.getPokokHutang() *
                    (kreditModel.getTenor() / 12) / 100;
            kreditModel.setNominalBunga(bunga);

//            Log.e("Final Bunga", kreditModel.getNominalBunga().toString());//belum
        }

        //PerhitunganBungaMotor
        if (kreditModel.getRateBungaMotor() != null && kreditModel.getPokokHutangTerhutang() != null &&
                kreditModel.getTenor() != null) {
            Double bungaMotor = (kreditModel.getRateBungaMotor() * kreditModel.getPokokHutangTerhutang()) / 100;
            kreditModel.setNominalBungaMotor(bungaMotor);
//            Log.e("Final Bunga Motor", kreditModel.getNominalBungaMotor().toString());
        }

        //PerhitunganAngsuran
        if (kreditModel.getProduk() != null) {
            if (kreditModel.getOtr() != null && kreditModel.getNominalBunga() != null) {
//                Log.e("Angsuran", "mobil");
                Double totalAngsuran = (kreditModel.getPokokHutang() + kreditModel.getNominalBunga()) / kreditModel.getTenor();
                Double finalAngsuranMobil = (Math.ceil(totalAngsuran/1000))*1000;
                kreditModel.setAngsuran(finalAngsuranMobil);
            } else if (kreditModel.getNominalBungaMotor() != null) {
//                Log.e("Angsuran", "motor");
                Double totalAngsuranMotor = (kreditModel.getPokokHutangTerhutang() + kreditModel.getNominalBungaMotor())
                        / kreditModel.getTenor();
                Double finalAngsuranMotor = (Math.ceil(totalAngsuranMotor/1000))*1000;
                kreditModel.setAngsuran(finalAngsuranMotor);
            }
//            Log.e("iAngsuran", kreditModel.getAngsuran().toString());
        }


        //PerhitunganProvisi
        if (kreditModel.getProduk() != null) {
            if (kreditModel.getPokokHutang() != null) {
                Double totalProvisi = kreditModel.getPokokHutang() * kreditModel.getRateProvisi() / 100;
                kreditModel.setBiayaProvisi(totalProvisi);
//                Log.e("Final Provisi Mobil", kreditModel.getBiayaProvisi().toString());
            } else if (kreditModel.getPokokHutangTerhutang() != null && kreditModel.getRateProvisi() != null) {
                Double totalProvisiMotor = kreditModel.getPokokHutangTerhutang() * kreditModel.getRateProvisi() / 100;
                kreditModel.setBiayaProvisi(totalProvisiMotor);
//                Log.e("Final Provisi Motor", kreditModel.getBiayaProvisi().toString());
            }

//            Log.e("Final Provisi", kreditModel.getBiayaProvisi().toString());
        }

        //PerhitunganTDP
        if (kreditModel.getUangMuka() != null && kreditModel.getAngsuran() != null &&
                kreditModel.getBiayaAdmin() != null && kreditModel.getBiayaFiducia() != null &&
                kreditModel.getBiayaSurvey() != null && kreditModel.getNominalAsuransiMobil() != null &&
                kreditModel.getAsuransiJiwa() != null && kreditModel.getBiayaProvisi() != null) {
//            Log.e("TDP Tenor", kreditModel.getTenor().toString());
//            Log.e("TDP Tahun", kreditModel.getUsiaKendaraan().toString());
//            Log.e("TDP Uang Muka", kreditModel.getUangMuka().toString());
//            Log.e("TDP Rate Bunga", kreditModel.getRateBunga().toString());
//            Log.e("TDP Total Bunga", kreditModel.getNominalBunga().toString());
//            Log.e("TDP Angsuran", kreditModel.getAngsuran().toString());
//            Log.e("TDP Biaya Admin", kreditModel.getBiayaAdmin().toString());
//            Log.e("TDP Biaya Fiducia", kreditModel.getBiayaFiducia().toString());
//            Log.e("TDP Biaya Survey", kreditModel.getBiayaSurvey().toString());
//            Log.e("TDP Rate Asuransi", kreditModel.getRateAsuransi().toString());
//            Log.e("TDP Biaya Asuransi", kreditModel.getNominalAsuransiMobil().toString());
//            Log.e("TDP Biaya Asuransi Jiwa", kreditModel.getAsuransiJiwa().toString());
//            Log.e("TDP Biaya Provisi", kreditModel.getBiayaProvisi().toString());
            Double totalTdp = kreditModel.getUangMuka() + (kreditModel.getAngsuran() +
                    kreditModel.getBiayaAdmin() + kreditModel.getBiayaFiducia() +
                    kreditModel.getBiayaSurvey() + kreditModel.getNominalAsuransiMobil() +
                    kreditModel.getAsuransiJiwa() + kreditModel.getBiayaProvisi());
            Double finalTdp = (Math.ceil(totalTdp/1000))*1000;
            kreditModel.setTdp(finalTdp);
//            Log.e("Total TDP", kreditModel.getTdp().toString());
        }

        //PerhitunganPencairan
        if (kreditModel.getBiayaProvisi() != null) {
            if (kreditModel.getProduk().equalsIgnoreCase("motor")) {
//            Log.e("Pn Biaya Admin", kreditModel.getBiayaAdmin().toString());
//            Log.e("Pn Biaya Fiducia", kreditModel.getBiayaFiducia().toString());
//            Log.e("Pn Biaya Survey", kreditModel.getBiayaSurvey().toString());
//            Log.e("Pn Rate Asuransi", kreditModel.getRateAsuransiMotor().toString());
//            Log.e("Pn Rate Bunga", kreditModel.getRateBunga().toString());
//            Log.e("Pn Nominal Bunga", kreditModel.getNominalBungaMotor().toString());
//            Log.e("Pn Biaya Asuransi", kreditModel.getNominalAsuransiMotor().toString());
//            Log.e("Pn Biaya Provisi", kreditModel.getBiayaProvisi().toString());
                Double pencairan = kreditModel.getPokokHutangMotor() - kreditModel.getBiayaProvisi();
                Double totalPencairan = (Math.ceil(pencairan/1000))*1000;
                kreditModel.setNominalPencairan(totalPencairan);
            } else if (kreditModel.getProduk().equalsIgnoreCase("mobil")){
//                Log.e("Pn Biaya Admin", kreditModel.getBiayaAdmin().toString());
//                Log.e("Pn Biaya Fiducia", kreditModel.getBiayaFiducia().toString());
//                Log.e("Pn Biaya Survey", kreditModel.getBiayaSurvey().toString());
//                Log.e("Pn Rate Asuransi", kreditModel.getRateAsuransi().toString());
//                Log.e("Pn Rate Bunga", kreditModel.getRateBunga().toString());
//                Log.e("Pn Nominal Bunga", kreditModel.getNominalBunga().toString());
//                Log.e("Pn Biaya Asuransi", kreditModel.getNominalAsuransiMobil().toString());
//                Log.e("Pn Biaya Provisi", kreditModel.getBiayaProvisi().toString());
                Double pencairan = kreditModel.getOtr() -(kreditModel.getOtr()*kreditModel.getMaxDpMobil()/100) -kreditModel.getTdp();
                Double totalPencairan = (Math.ceil(pencairan/1000))*1000;
                kreditModel.setNominalPencairan(totalPencairan);
            }
        }


        return kreditModel;
    }

}