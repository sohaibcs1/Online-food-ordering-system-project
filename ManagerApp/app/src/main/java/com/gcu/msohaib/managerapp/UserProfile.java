package com.gcu.msohaib.managerapp;


public class UserProfile {
    public String Name;
    public String Phone;
    public String NIC;
    public String Adress;
    public String Customer_id;
    public String date;
    public String Order_id;
    public String NoOfDeal_Order;
    public String Boy_id;


    public UserProfile(){
    }

    public UserProfile(String Name, String Phone, String Adress , String NIC , String cid,String dt,String oid, String NoOfDeal_Order,String bid) {
        this.Name = Name;
        this.Phone = Phone;
        this.Adress = Adress;
        this.NIC = NIC;
        this.Customer_id= cid;
        this.date= dt;
        this.Order_id=oid;
        this.NoOfDeal_Order=NoOfDeal_Order;
        this.Boy_id=bid;
    }

    public String getboy() {return Boy_id;}
    public void setboy(String Boy_id){this.Boy_id = Boy_id;}

    public String getnoOforder() {return NoOfDeal_Order;}
    public void setnoOforder(String NoOfDeal_Order){this.NoOfDeal_Order = NoOfDeal_Order;}


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
