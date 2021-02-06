package com.gcu.msohaib.managerapp;


public class UserProfile2 {
    public String customerPhone;
    public String CustomerName;
    public String fuel;
    public String customerAddress;
    public String deliveryBoyId;
    public String  date;
    public String bill;
    public String salary;
    public String lat;
    public String lng;
    public String area;

    public UserProfile2(){
    }

    public UserProfile2(String customerP,String CustomerName,String fuel, String customerAddress ,String deliveryBoyId,String date,String bill,String salary, String lat,String lng,String area) {
        this.customerPhone = customerP;
        this.CustomerName = CustomerName;
        this.fuel=fuel;
        this.customerAddress=customerAddress;
        this.deliveryBoyId=deliveryBoyId;
        this.date=date;
        this.bill=bill;
        this.salary=salary;
        this.lat=lat;
        this.lng=lng;
        this.area=area;
    }
    public String getarea() {return area;}
    public void setarea(String area){this.area = area;}

    public String getlat() {return lat;}
    public void setlat(String lat){this.lat = lat;}

    public String getlng() {return lng;}
    public void setlng(String lng){this.lng = lng;}

    public String getsalary() {return salary;}
    public void setsalary(String salary){this.salary = salary;}

    public String getbill() {return bill;}
    public void setbill(String bill){this.bill = bill;}

    public String getdate() {return date;}
    public void setdate(String date){this.date = date;}

    public String getid() {return deliveryBoyId;}
    public void setid(String deliveryBoyId){this.deliveryBoyId = deliveryBoyId;}

    public String getaddress() {return customerAddress;}
    public void setaddress(String customerAddress){this.customerAddress = customerAddress;}

    public String getfuel() {return fuel;}
    public void setfuel(String fuel){this.fuel = fuel;}

    public String getCustomerPhone() {return customerPhone;}
    public void setCustomerPhone(String customerPhone){this.customerPhone = customerPhone;}

    public String getcustomer() {return CustomerName;}
    public void setcustomer(String CustomerName){this.CustomerName = CustomerName;}


}
