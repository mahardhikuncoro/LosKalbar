package ops;

import android.content.Context;

import java.util.List;

import base.sqlite.IndicatorHarga;
import base.sqlite.IndicatorHargaModel;
import base.sqlite.IndicatorTahun;
import base.sqlite.IndicatorTahunModel;
import base.sqlite.ParamKategoriAsuransi;
import base.sqlite.ParamKategoriAsuransiModel;
import base.sqlite.ParamPenyusutan;
import base.sqlite.ParamPenyusutanModel;
import base.sqlite.ParameterPinjaman;
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
import ops.screen.KreditModel;

/**
 * Created by christian on 4/9/18.
 */

public class SimulasiKredit2 {

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

//        Log.e("cek Harga", kreditModel.getDana().toString());
//        Log.e("cek Dp", kreditModel.getUangMuka().toString());
//        Log.e("cek Produk", kreditModel.getProduk());
//        Log.e("cek Paket", kreditModel.getPaket());
//        Log.e("cek Tenor", kreditModel.getTenor().toString());


//        indicatorHarga
        if (kreditModel.getDana() !=null){
//            Log.e("Indicator harga", "ini");
            indicatorHargaModel = indicatorHarga.selectBetween(kreditModel.getDana());
            kreditModel.setIndicatorHarga(indicatorHargaModel.getIndicator_harga());
//            Log.e("iHarga", indicatorHargaModel.getIndicator_harga());
        }

        //indicatorTahun
        if (kreditModel.getUsiaKendaraan() != null) {
//            Log.e("Indicator tahun", "ini");
            indicatorTahunModel = indicatorTahun.selectBetween(kreditModel.getUsiaKendaraan());
            kreditModel.setIndicatorTahun(indicatorTahunModel.getIndicator_tahun());
//            Log.e("iTahun", indicatorTahunModel.getIndicator_tahun());
        }

        //kategoriAsuransi
        if (kreditModel.getUsiaKendaraan() != null) {
//            Log.e("Usia Kendaraan", "ini");
            paramKategoriAsuransiModel = paramKategoriAsuransi.selectBetween(kreditModel.getUsiaKendaraan());
            kreditModel.setKategoriAsuransi(paramKategoriAsuransiModel.getValue());
//            Log.e("iKatAsuransi", kreditModel.getKategoriAsuransi());
        }

        //maxTenor
        if (kreditModel.getProduk() != null && kreditModel.getUsiaKendaraan() != null) {
//            Log.e("Max Tenor", "ini");
            parameterTenorModel = parameterTenor.selectBetween(kreditModel.getProduk(), kreditModel.getUsiaKendaraan());
            kreditModel.setMaxTenor(parameterTenorModel.getValue());
//            Log.e("iMaxTenor", kreditModel.getMaxTenor().toString());
        }

        //penyusutan
        if (kreditModel.getTenor() != null && kreditModel.getProduk()!=null) {
//            Log.e("Penyusutan", "ini");
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                paramPenyusutanModel = paramPenyusutan.selectBy(kreditModel.getTenor() / 12);
                kreditModel.setPenyusutan(paramPenyusutanModel.getValue());
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")){
//                Log.e("penyusutan", "motor");
                paramPenyusutanModel = paramPenyusutan.selectBy(kreditModel.getTenor()/6);
                kreditModel.setPenyusutan(paramPenyusutanModel.getValue());
            }
//            Log.e("penyusutan", kreditModel.getMaxTenor().toString());
        }

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
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")){
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

        /*//rateAsuransi
        if (kreditModel.getPaket() != null && kreditModel.getIndicatorHarga() !=null &&
                kreditModel.getKategoriAsuransi() != null ) {
//            Log.e("Rate Asuransi", "ini");
            rateAsuransiModel = rateAsuransi.selectBy(kreditModel.getPaket(),
                    kreditModel.getIndicatorHarga(), kreditModel.getKategoriAsuransi());
            if (kreditModel.getWilayah() == 1) {
                kreditModel.setRateAsuransi(rateAsuransiModel.getRateWilayah1());
            } else if (kreditModel.getWilayah() == 2) {
                kreditModel.setRateAsuransi(rateAsuransiModel.getRateWilayah2());
            } else if (kreditModel.getWilayah() == 3) {
                kreditModel.setRateAsuransi(rateAsuransiModel.getRateWilayah3());
            }*//* else {
                kreditModel.setRateAsuransi(0D);
            }*//*
//            Log.e("iWilayah", kreditModel.getWilayah().toString());
//            Log.e("iRateAsuransi", kreditModel.getRateAsuransi().toString());
        }*/

        //rateAsuransiMotor
        if (kreditModel.getPaket()!=null && kreditModel.getProduk()!=null &&
                kreditModel.getTenor()!=null){
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
        if (kreditModel.getDana()!=null){
            rateAsuransiJiwaModel = rateAsuransiJiwa.selectBetween(kreditModel.getDana());
            if (kreditModel.getTenor()==12){
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_1());
            } else if (kreditModel.getTenor()==24){
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_2());
            } else if (kreditModel.getTenor()==36){
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_3());
            } else if (kreditModel.getTenor()==48){
                kreditModel.setAsuransiJiwa(rateAsuransiJiwaModel.getBiaya_tahun_4());
            }
//            Log.e("iRateASJ", kreditModel.getAsuransiJiwa().toString());
        }

        /*//PerhitunganBiaya
        if (kreditModel.getPaket()!=null && kreditModel.getProduk() !=null ){
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                List<RateBiayaModel> list = rateBiaya.selectBy(kreditModel.getPaket(),
                        kreditModel.getProduk(), kreditModel.getTenor() / 12);
//                Log.e("size mobil", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getNamaBiaya().equalsIgnoreCase("survey")) {
                        kreditModel.setBiayaSurvey(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("fiducia")) {
                        kreditModel.setBiayaFiducia(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("administrasi")) {
                        kreditModel.setBiayaAdmin(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("provisi")) {
                        kreditModel.setRateProvisi(list.get(i).getValue());
                    }
                }
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")){
                List<RateBiayaModel> list = rateBiaya.selectBy(kreditModel.getPaket(),
                        kreditModel.getProduk(), kreditModel.getTenor());
//                Log.e("size motor", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getNamaBiaya().equalsIgnoreCase("survey")) {
//                    Log.e("survey",list.get(i).getValue().toString());
                        kreditModel.setBiayaSurvey(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("fiducia")) {
                        kreditModel.setBiayaFiducia(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("administrasi")) {
                        kreditModel.setBiayaAdmin(list.get(i).getValue());
                    } else if (list.get(i).getNamaBiaya().equalsIgnoreCase("provisi")) {
                        kreditModel.setRateProvisi(list.get(i).getValue());
                    }
                }
            }

//            Log.e("iSurvey", kreditModel.getBiayaSurvey().toString());
//            Log.e("iAdmin", kreditModel.getBiayaAdmin().toString());
//            Log.e("iFiducia", kreditModel.getBiayaFiducia().toString());
//            Log.e("iRateProvisi", kreditModel.getRateProvisi().toString());

        }*/

        //PerhitunganAsuransi
        if (kreditModel.getRateAsuransi() != null && kreditModel.getTenor() != null &&
                kreditModel.getDana() != null && kreditModel.getTahunKendaraan() != null &&
                kreditModel.getProduk()!=null) {
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
                if (kreditModel.getPenyusutan()!=null){

                }

            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")){
//                Log.e("Asuransi", "Motor");
                Double asuransiMotor = kreditModel.getRateAsuransiMotor() * kreditModel.getDana() / 100;
                kreditModel.setNominalAsuransiMotor(asuransiMotor);
            }
//            Log.e("Final Asuransi", kreditModel.getNominalAsuransi().toString());
        }

        //PerhitunganPH
        if (kreditModel.getDana() != null && kreditModel.getUangMuka() != null &&
                kreditModel.getProduk()!=null) {
            if (kreditModel.getProduk().equalsIgnoreCase("mobil")) {
//                Log.e("PH murni", "mobil");
                Double ph = kreditModel.getDana() - kreditModel.getUangMuka();
                kreditModel.setPokokHutang(ph);
            } else if (kreditModel.getProduk().equalsIgnoreCase("motor")){
//                Log.e("PH terutang", "motor");
                Double phTerhutang = kreditModel.getDana() + kreditModel.getBiayaAdmin() +
                        kreditModel.getBiayaSurvey() + kreditModel.getBiayaFiducia()
                        + kreditModel.getNominalAsuransiMotor();
                kreditModel.setPokokHutangTerhutang(phTerhutang);
            }

//            Log.e("Final PH", kreditModel.getPokokHutang().toString());
        }

        //PerhitunganBungaMobil
        if (kreditModel.getRateBunga() != null && kreditModel.getPokokHutang() != null &&
                kreditModel.getTenor() != null) {
            Double bunga = kreditModel.getRateBunga() * kreditModel.getPokokHutang() *
                    (kreditModel.getTenor() / 12) / 100;
            kreditModel.setNominalBunga(bunga);

//            Log.e("Final Bunga", kreditModel.getNominalBunga().toString());
        }

        //PerhitunganBungaMotor
        if (kreditModel.getRateBungaMotor()!=null && kreditModel.getPokokHutangTerhutang() !=null &&
                kreditModel.getTenor()!=null){
            Double bungaMotor = (kreditModel.getRateBungaMotor() * kreditModel.getPokokHutangTerhutang()) / 100;
            kreditModel.setNominalBungaMotor(bungaMotor);
//            Log.e("Final Bunga Motor", kreditModel.getNominalBungaMotor().toString());
        }

        //PerhitunganAngsuran
        if (kreditModel.getProduk()!=null) {
            if (kreditModel.getDana()!=null && kreditModel.getNominalBunga()!=null) {
                Double totalAngsuran = (kreditModel.getDana() + kreditModel.getNominalBunga()) / kreditModel.getTenor();
                kreditModel.setAngsuran(totalAngsuran);
            } else if (kreditModel.getNominalBungaMotor()!=null){
                Double totalAngsuranMotor = (kreditModel.getPokokHutangTerhutang() + kreditModel.getNominalBungaMotor())
                        / kreditModel.getTenor();
                kreditModel.setAngsuran(totalAngsuranMotor);
            }
//            Log.e("iAngsuran", kreditModel.getAngsuran().toString());
        }


        //PerhitunganProvisi
        if (kreditModel.getProduk()!=null ){
            if (kreditModel.getPokokHutang()!=null) {
//                Double totalProvisi = kreditModel.getPokokHutang() * kreditModel.getRateProvisi() / 100;
//                kreditModel.setBiayaProvisi(totalProvisi);
//            Log.e("Final Provisi", kreditModel.getBiayaProvisi().toString());
            } else if (kreditModel.getPokokHutangTerhutang()!=null && kreditModel.getRateProvisi()!=null ){
                Double totalProvisiMotor = kreditModel.getPokokHutangTerhutang() * kreditModel.getRateProvisi() / 100;
                kreditModel.setBiayaProvisi(totalProvisiMotor);
            }

//            Log.e("Final Provisi", kreditModel.getBiayaProvisi().toString());
        }

        //PerhitunganTDP
        if (kreditModel.getUangMuka()!=null && kreditModel.getAngsuran()!=null &&
                kreditModel.getBiayaAdmin()!=null && kreditModel.getBiayaFiducia()!=null &&
                kreditModel.getBiayaSurvey()!=null && kreditModel.getNominalAsuransiMobil()!=null &&
                kreditModel.getAsuransiJiwa()!= null && kreditModel.getBiayaProvisi()!=null){
            Double totalTdp = kreditModel.getUangMuka() + (kreditModel.getAngsuran() +
                    kreditModel.getBiayaAdmin() + kreditModel.getBiayaFiducia() +
                    kreditModel.getBiayaSurvey() + kreditModel.getNominalAsuransiMobil() +
                    kreditModel.getAsuransiJiwa() + kreditModel.getBiayaProvisi());
        }

        //PerhitunganPencairan
        if (kreditModel.getDana()!=null && kreditModel.getBiayaProvisi()!=null){
            Double pencairan = kreditModel.getDana() - kreditModel.getBiayaProvisi();
            kreditModel.setNominalPencairan(pencairan);
        }





        return kreditModel;
    }

}
