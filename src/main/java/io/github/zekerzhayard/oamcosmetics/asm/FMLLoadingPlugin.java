package io.github.zekerzhayard.oamcosmetics.asm;

import java.util.Map;

import io.github.zekerzhayard.oamcosmetics.OAMCosmetics;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(value = OAMCosmetics.NAME)
@IFMLLoadingPlugin.MCVersion(value = "1.8.9")
@IFMLLoadingPlugin.SortingIndex(value = 500)
public class FMLLoadingPlugin implements IFMLLoadingPlugin {
    @Override()
    public String[] getASMTransformerClass() {
        return new String[] {ClassTransformer.class.getName()};
    }

    @Override()
    public String getModContainerClass() {
        return OAMCosmetics.class.getName();
    }

    @Override()
    public String getSetupClass() {
        return null;
    }

    @Override()
    public void injectData(Map<String, Object> data) {

    }

    @Override()
    public String getAccessTransformerClass() {
        return null;
    }
}
