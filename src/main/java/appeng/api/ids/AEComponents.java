package appeng.api.ids;

import appeng.core.AppEng;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

public final class AEComponents {
    private AEComponents() {
    }

    private static DeferredRegister<DataComponentType<?>> DR = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AppEng.MOD_ID);

    @ApiStatus.Internal
    public static void finalizeRegistration(IEventBus bus) {
        DR.register(bus);
    }
}
