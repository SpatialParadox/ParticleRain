package condolence.particlerain.client.particle;

import condolence.particlerain.common.ParticleRain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    // Create particle registry
    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ParticleRain.MOD_ID);

    // Register particles
    public static final RegistryObject<ParticleType<BasicParticleType>> RAIN_DROP = PARTICLES.register("rain_drop", () -> new BasicParticleType(false));
    public static final RegistryObject<ParticleType<BasicParticleType>> SNOW_FLAKE = PARTICLES.register("snow_flake", () -> new BasicParticleType(false));
    public static final RegistryObject<ParticleType<BasicParticleType>> DESERT_DUST = PARTICLES.register("desert_dust", () -> new BasicParticleType(false));

    public static void registerParticles(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    public static void onParticleFactoryRegistration(ParticleFactoryRegisterEvent event) {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        particleManager.registerFactory(RAIN_DROP.get(), RainDropParticle.Factory::new);
        particleManager.registerFactory(SNOW_FLAKE.get(), SnowFlakeParticle.Factory::new);
        particleManager.registerFactory(DESERT_DUST.get(), DesertDustParticle.Factory::new);
    }
}
