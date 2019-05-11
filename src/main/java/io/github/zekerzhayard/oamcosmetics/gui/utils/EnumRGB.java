package io.github.zekerzhayard.oamcosmetics.gui.utils;

public enum EnumRGB {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue");
    
    private String commonName;
    
    private EnumRGB(String commonName) {
        this.commonName = commonName;
    }
    
    public String getCommonName() {
        return this.commonName;
    }
}
