package  com.stop.smoking.home.repository.dataBase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class Profile {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    public String quittingDate;
    public int cigarettesPerDay;
    public int cigarettesInPack;
    public int yearsOfSmoking;
    public int pricePerPack;
    public String devise;
    public float moneySpentOnRewards;

    public Profile(@NonNull String id, String quittingDate, int cigarettesPerDay, int cigarettesInPack, int yearsOfSmoking, int pricePerPack,String devise,float moneySpentOnRewards) {
        this.id = id;
        this.quittingDate=quittingDate;
        this.cigarettesPerDay=cigarettesPerDay;
        this.cigarettesInPack=cigarettesInPack;
        this.yearsOfSmoking=yearsOfSmoking;
        this.pricePerPack=pricePerPack;
        this.devise=devise;
        this.moneySpentOnRewards=moneySpentOnRewards;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getQuittingDate() {
        return quittingDate;
    }

    public void setQuittingDate(String quittingDate) {
        this.quittingDate = quittingDate;
    }

    public int getCigarettesPerDay() {
        return cigarettesPerDay;
    }

    public void setCigarettesPerDay(int cigarettesPerDay) {
        this.cigarettesPerDay = cigarettesPerDay;
    }

    public int getCigarettesInPack() {
        return cigarettesInPack;
    }

    public void setCigarettesInPack(int cigarettesInPack) {
        this.cigarettesInPack = cigarettesInPack;
    }

    public int getYearsOfSmoking() {
        return yearsOfSmoking;
    }

    public void setYearsOfSmoking(int yearsOfSmoking) {
        this.yearsOfSmoking = yearsOfSmoking;
    }

    public int getPricePerPack() {
        return pricePerPack;
    }

    public void setPricePerPack(int pricePerPack) {
        this.pricePerPack = pricePerPack;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public float getMoneySpentOnRewards() {
        return moneySpentOnRewards;
    }

    public void setMoneySpentOnRewards(float moneySpentOnRewards) {
        this.moneySpentOnRewards = moneySpentOnRewards;
    }
}
