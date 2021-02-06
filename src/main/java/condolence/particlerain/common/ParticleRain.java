package condolence.particlerain.common;

import condolence.particlerain.client.event.ParticleHandler;
import condolence.particlerain.client.particle.ModParticles;
import condolence.particlerain.common.config.ClientConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ParticleRain.MOD_ID)
public class ParticleRain {
    public static final String MOD_ID = "particlerain";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public ParticleRain() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

            ModParticles.registerParticles(eventBus);
            eventBus.addListener(ModParticles::onParticleFactoryRegistration);
            eventBus.addListener(ClientConfig::onModConfigEvent);

            MinecraftForge.EVENT_BUS.addListener(ParticleHandler::onClientTick);
        }
    }
}
