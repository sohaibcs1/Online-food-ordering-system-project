package com.gcu.msohaib.boy;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

public class UserProfile {
    public String Name;
    public String Phone;
    public String NIC;
    public String Adress;
    public String Customer_id;
    public String date;
    public String Order_id;
    public String Boy_id;
    public String deal_number;
    public String area;
    public String bill;
    @Exclude private String id;



    public UserProfile(){
    }

    public UserProfile(String Name, String Phone, String Adress , String NIC , String cid,String dt,String oid,String bid ,String deal_number,String area,String bill) {
        this.Name = Name;
        this.Phone = Phone;
        this.Adress = Adress;
        this.NIC = NIC;
        this.Customer_id= cid;
        this.date= dt;
        this.Order_id=oid;
        this.Boy_id=bid;
        this.deal_number=deal_number;
        this.area=area;
        this.bill=bill;
    }

    public String getarea() {return area;}
    public void setarea(String area){this.area = area;}

    public String getdnumber() {return deal_number;}
    public void setdnumber(String deal_number){this.deal_number = deal_number;}

    public String getId(){return id; }

    public void setId(){this.id=id;}

    public String getboy() {return Boy_id;}
    public void setboy(String Boy_id){this.Boy_id = Boy_id;}

    public String getbill() {return bill;}
    public void setbill(String bill){this.bill = bill;}


    public String getorder() {return Order_id;}
    public void setorder(String Order_id){this.Order_id = Order_id;}

    public String getdate() {return date;}
    public void setdate(String date){this.date = date;}

    public String getcid() {return Customer_id;}
    public void setcid(String cid){this.Customer_id = cid;}

    public String getUserName() {return Name;}
    public void setUserName(String Name){this.Name = Name;}

    public String getUserPhone() {return Phone;}
    public void setUserPhone(String Phone) {this.Phone = Phone;}



    public String getUserAdress() {
        return Adress;
    }
    public void setUserAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getUserNic() {
        return NIC;
    }
    public void setUserNic(String NIC) {
        this.NIC = NIC;
    }

}
