package icbm.classic.content.blocks.emptower;

import net.minecraft.tileentity.TileEntity;

public class TileEmpTowerFake extends TileEntity {

    private TileEMPTower host;

    public TileEMPTower getHost() {
        return host;
    }

    public void setHost(TileEMPTower tower) {
        this.host = tower;
    }

}
