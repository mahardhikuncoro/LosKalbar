package ops.screen;

import java.io.Serializable;

/**
 * Created by christian on 4/6/18.
 */

public class KreditModel implements Serializable {

    private Integer id;
    private String tipeKendaraan;
    private Double dana;
    private Double uangMuka;
    private Integer tahunKendaraan;
    private Integer usiaKendaraan;
    private String korwilid;
    private String kotaid;
    private Integer wilayah;
    private Integer tenor;
    private Double pokokHutang;
    private Double pokokHutangMotor;
    private Double pokokHutangTerhutang;
    private Double angsuran;
    private String paket;
    private String produk;
    private String indicatorTahun;
    private String indicatorHarga;
    private String kategoriAsuransi;
    private Integer maxTenor;
    private Double rateAsuransi;
    private Double rateAsuransiTLO;
    private Double rateAsuransiOver;
    private Double rateAsuransiMotor;
    private Double rateBunga;
    private Double rateBungaMotor;
    private Double nominalBunga;
    private Double nominalBungaMotor;
    private Double nominalAsuransiMobil;
    private Double nominalAsuransiMobil2;
    private Double nominalAsuransiMotor;
    private Double rateProvisi;
    private Double biayaProvisi;
    private Double biayaAdmin;
    private Double biayaSurvey;
    private Double biayaFiducia;
    private Double asuransiJiwa;
    private Double tdp;
    private Double nominalTdp;
    private Double totalA;
    private Double nominalPencairan;
    private Double penyusutan;
    private Double otr;
    private Double otrPenyusutan;

    private String disclaimerResult1;
    private String disclaimerResult2;

    private String disclaimerResult12;
    private String disclaimerResult22;

    private Integer maxDpMobil;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipeKendaraan() {
        return tipeKendaraan;
    }

    public void setTipeKendaraan(String tipeKendaraan) {
        this.tipeKendaraan = tipeKendaraan;
    }

    public Double getDana() {
        return dana;
    }

    public void setDana(Double dana) {
        this.dana = dana;
    }

    public Double getUangMuka() {
        return uangMuka;
    }

    public void setUangMuka(Double uangMuka) {
        this.uangMuka = uangMuka;
    }

    public Integer getTahunKendaraan() {
        return tahunKendaraan;
    }

    public void setTahunKendaraan(Integer tahunKendaraan) {
        this.tahunKendaraan = tahunKendaraan;
    }

    public Integer getUsiaKendaraan() {
        return usiaKendaraan;
    }

    public void setUsiaKendaraan(Integer usiaKendaraan) {
        this.usiaKendaraan = usiaKendaraan;
    }

    public String getKorwilid() {
        return korwilid;
    }

    public void setKorwilid(String korwilid) {
        this.korwilid = korwilid;
    }

    public String getKotaid() {
        return kotaid;
    }

    public void setKotaid(String kotaid) {
        this.kotaid = kotaid;
    }

    public Integer getWilayah() {
        return wilayah;
    }

    public void setWilayah(Integer wilayah) {
        this.wilayah = wilayah;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public Double getPokokHutang() {
        return pokokHutang;
    }

    public void setPokokHutang(Double pokokHutang) {
        this.pokokHutang = pokokHutang;
    }

    public Double getPokokHutangMotor() {
        return pokokHutangMotor;
    }

    public void setPokokHutangMotor(Double pokokHutangMotor) {
        this.pokokHutangMotor = pokokHutangMotor;
    }

    public Double getPokokHutangTerhutang() {
        return pokokHutangTerhutang;
    }

    public void setPokokHutangTerhutang(Double pokokHutangTerhutang) {
        this.pokokHutangTerhutang = pokokHutangTerhutang;
    }

    public Double getAngsuran() {
        return angsuran;
    }

    public void setAngsuran(Double angsuran) {
        this.angsuran = angsuran;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getIndicatorTahun() {
        return indicatorTahun;
    }

    public void setIndicatorTahun(String indicatorTahun) {
        this.indicatorTahun = indicatorTahun;
    }

    public String getIndicatorHarga() {
        return indicatorHarga;
    }

    public void setIndicatorHarga(String indicatorHarga) {
        this.indicatorHarga = indicatorHarga;
    }

    public String getKategoriAsuransi() {
        return kategoriAsuransi;
    }

    public void setKategoriAsuransi(String kategoriAsuransi) {
        this.kategoriAsuransi = kategoriAsuransi;
    }

    public Integer getMaxTenor() {
        return maxTenor;
    }

    public void setMaxTenor(Integer maxTenor) {
        this.maxTenor = maxTenor;
    }

    public Double getRateAsuransi() {
        return rateAsuransi;
    }

    public void setRateAsuransi(Double rateAsuransi) {
        this.rateAsuransi = rateAsuransi;
    }

    public Double getRateAsuransiTLO() {
        return rateAsuransiTLO;
    }

    public void setRateAsuransiTLO(Double rateAsuransiTLO) {
        this.rateAsuransiTLO = rateAsuransiTLO;
    }

    public Double getRateAsuransiMotor() {
        return rateAsuransiMotor;
    }

    public void setRateAsuransiMotor(Double rateAsuransiMotor) {
        this.rateAsuransiMotor = rateAsuransiMotor;
    }

    public Double getRateBunga() {
        return rateBunga;
    }

    public void setRateBunga(Double rateBunga) {
        this.rateBunga = rateBunga;
    }

    public Double getRateBungaMotor() {
        return rateBungaMotor;
    }

    public void setRateBungaMotor(Double rateBungaMotor) {
        this.rateBungaMotor = rateBungaMotor;
    }

    public Double getNominalBunga() {
        return nominalBunga;
    }

    public void setNominalBunga(Double nominalBunga) {
        this.nominalBunga = nominalBunga;
    }

    public Double getNominalBungaMotor() {
        return nominalBungaMotor;
    }

    public void setNominalBungaMotor(Double nominalBungaMotor) {
        this.nominalBungaMotor = nominalBungaMotor;
    }

    public Double getNominalAsuransiMobil() {
        return nominalAsuransiMobil;
    }

    public void setNominalAsuransiMobil(Double nominalAsuransiMobil) {
        this.nominalAsuransiMobil = nominalAsuransiMobil;
    }

    public Double getNominalAsuransiMotor() {
        return nominalAsuransiMotor;
    }

    public void setNominalAsuransiMotor(Double nominalAsuransiMotor) {
        this.nominalAsuransiMotor = nominalAsuransiMotor;
    }

    public Double getRateProvisi() {
        return rateProvisi;
    }

    public void setRateProvisi(Double rateProvisi) {
        this.rateProvisi = rateProvisi;
    }

    public Double getBiayaProvisi() {
        return biayaProvisi;
    }

    public void setBiayaProvisi(Double biayaProvisi) {
        this.biayaProvisi = biayaProvisi;
    }

    public Double getBiayaAdmin() {
        return biayaAdmin;
    }

    public void setBiayaAdmin(Double biayaAdmin) {
        this.biayaAdmin = biayaAdmin;
    }

    public Double getBiayaSurvey() {
        return biayaSurvey;
    }

    public void setBiayaSurvey(Double biayaSurvey) {
        this.biayaSurvey = biayaSurvey;
    }

    public Double getBiayaFiducia() {
        return biayaFiducia;
    }

    public void setBiayaFiducia(Double biayaFiducia) {
        this.biayaFiducia = biayaFiducia;
    }

    public Double getAsuransiJiwa() {
        return asuransiJiwa;
    }

    public void setAsuransiJiwa(Double asuransiJiwa) {
        this.asuransiJiwa = asuransiJiwa;
    }

    public Double getTdp() {
        return tdp;
    }

    public void setTdp(Double tdp) {
        this.tdp = tdp;
    }

    public Double getNominalTdp() {
        return nominalTdp;
    }

    public void setNominalTdp(Double nominalTdp) {
        this.nominalTdp = nominalTdp;
    }

    public Double getTotalA() {
        return totalA;
    }

    public void setTotalA(Double totalA) {
        this.totalA = totalA;
    }

    public Double getNominalPencairan() {
        return nominalPencairan;
    }

    public void setNominalPencairan(Double nominalPencairan) {
        this.nominalPencairan = nominalPencairan;
    }

    public Double getPenyusutan() {
        return penyusutan;
    }

    public void setPenyusutan(Double penyusutan) {
        this.penyusutan = penyusutan;
    }

    public Double getOtrPenyusutan() {
        return otrPenyusutan;
    }

    public void setOtrPenyusutan(Double otrPenyusutan) {
        this.otrPenyusutan = otrPenyusutan;
    }

    public Double getOtr() {
        return otr;
    }

    public void setOtr(Double otr) {
        this.otr = otr;
    }

    public Double getNominalAsuransiMobil2() {
        return nominalAsuransiMobil2;
    }

    public void setNominalAsuransiMobil2(Double nominalAsuransiMobil2) {
        this.nominalAsuransiMobil2 = nominalAsuransiMobil2;
    }

    public String getDisclaimerResult1() {
        return disclaimerResult1;
    }

    public void setDisclaimerResult1(String disclaimerResult1) {
        this.disclaimerResult1 = disclaimerResult1;
    }

    public String getDisclaimerResult2() {
        return disclaimerResult2;
    }

    public void setDisclaimerResult2(String disclaimerResult2) {
        this.disclaimerResult2 = disclaimerResult2;
    }

    public String getDisclaimerResult12() {
        return disclaimerResult12;
    }

    public void setDisclaimerResult12(String disclaimerResult12) {
        this.disclaimerResult12 = disclaimerResult12;
    }

    public String getDisclaimerResult22() {
        return disclaimerResult22;
    }

    public void setDisclaimerResult22(String disclaimerResult22) {
        this.disclaimerResult22 = disclaimerResult22;
    }

    public Integer getMaxDpMobil() {
        return maxDpMobil;
    }

    public void setMaxDpMobil(Integer maxDpMobil) {
        this.maxDpMobil = maxDpMobil;
    }

    public Double getRateAsuransiOver() {
        return rateAsuransiOver;
    }

    public void setRateAsuransiOver(Double rateAsuransiOver) {
        this.rateAsuransiOver = rateAsuransiOver;
    }
}
