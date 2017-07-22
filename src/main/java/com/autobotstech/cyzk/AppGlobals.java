package com.autobotstech.cyzk;

import android.app.Application;

/**
 * Created by zhi on 02/07/2017.
 */

public class AppGlobals extends Application {

    public String businessType;
    public String vehicleType;
    public String carStandard;
    public String useProperty;

    public String getCurrentFlowId() {
        return currentFlowId;
    }

    public void setCurrentFlowId(String currentFlowId) {
        this.currentFlowId = currentFlowId;
    }

    public String currentFlowId;


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCarStandard() {
        return carStandard;
    }

    public void setCarStandard(String carStandard) {
        this.carStandard = carStandard;
    }

    public String getUseProperty() {
        return useProperty;
    }

    public void setUseProperty(String useProperty) {
        this.useProperty = useProperty;
    }

}