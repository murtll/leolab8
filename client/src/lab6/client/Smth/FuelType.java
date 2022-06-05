package lab6.client.Smth;

import java.util.Objects;

/**
 * FuelType
 *
 * @author Nagorny Leonid
 */

public enum FuelType {
    GASOLINE(1, "GASOLINE"),
    KEROSENE(2,"KEROSENE"),
    DIESEL(3,"DIESEL"),
    ALCOHOL(4,"ALCOHOL"),
    NUCLEAR(5,"NUCLEAR");

    private FuelType(int fc, String name){
        this.forCompare = fc;
        this.name = name;
    }

    public int forCompare;
    private final String name;

    static public FuelType getObjByStr(String  str){
        for (FuelType fuelType : FuelType.values()){
            if (Objects.equals(fuelType.name, str)){
                return  fuelType;
            }
        }
        return null;
    }

}
