package condolence.particlerain.client.event;

import condolence.particlerain.client.particle.ModParticles;
import condolence.particlerain.client.util.MathUtil;
import condolence.particlerain.client.util.WorldUtil;
import condolence.particlerain.common.ParticleRain;
import condolence.particlerain.common.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.TickEvent;
import java.util.Random;

public class ParticleHandler {
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        // No need to do anything if the game is paused or the world/player don't exist
        if (minecraft.isGamePaused() || minecraft.world == null || minecraft.player == null) { return; }

        ClientWorld world = minecraft.world;

        if (world.isRaining() || world.isThundering()) {
            Random rand = world.getRandom();

            for (int pass = 0; pass < ClientConfig.particleDensity; pass++) {
                // Select a random position from a circular area centered at the player
                BlockPos.Mutable particlePos = WorldUtil.getRandomPosInRadius(rand, minecraft.player.getPosition(), ClientConfig.spawnRadius,5, ClientConfig.spawnRadius);

                // Check if precipitation is occurring at the selected position to avoid particles spawning indoors
                if (!WorldUtil.isPrecipitatingAt(world, particlePos)) { continue; }

                Biome particleBiome = world.getBiome(particlePos);

                // Spawn rain particle if the biome is a biome in which rain occurs
                if (particleBiome.getPrecipitation() == Biome.RainType.RAIN) {
                    BlockPos.Mutable tempCheckPos = new BlockPos.Mutable(
                            particlePos.getX(),
                            0,
                            particlePos.getZ()
                    );

                    // Check the temperature at the ground below where the particle should spawn
                    tempCheckPos.setY(world.getHeight(Heightmap.Type.MOTION_BLOCKING, tempCheckPos).getY());

                    if (particleBiome.getTemperature(tempCheckPos) >= 0.15F) {
                        world.addParticle((IParticleData) ModParticles.RAIN_DROP.get(),
                                particlePos.getX() + rand.nextFloat(),
                                particlePos.getY() + rand.nextFloat(),
                                particlePos.getZ() + rand.nextFloat(),
                                0, 0, 0);
                    } else {
                        world.addParticle((IParticleData) ModParticles.SNOW_FLAKE.get(),
                                particlePos.getX() + rand.nextFloat(),
                                particlePos.getY() + rand.nextFloat(),
                                particlePos.getZ() + rand.nextFloat(),
                                0, 0, 0);
                    }
                    continue;
                }

                // Spawn snow particle if the biome is as biome in which snow occurs
                if (particleBiome.getPrecipitation() == Biome.RainType.SNOW) {
                    ParticleRain.LOGGER.info("Biome - SNOW");
                    world.addParticle((IParticleData) ModParticles.SNOW_FLAKE.get(),
                            particlePos.getX() + rand.nextFloat(),
                            particlePos.getY() + rand.nextFloat(),
                            particlePos.getZ() + rand.nextFloat(),
                            0, 0, 0);
                    continue;
                }

                // Spawn dust particle if the biome is a desert biome
                if (particleBiome.getCategory() == Biome.Category.DESERT) {
                    // Set posY to use a lower bound of 0 for the y pos as it looks a bit better with sand storms
                    particlePos.setY((int) minecraft.player.getPosY() + MathUtil.nextIntInRange(rand, 0, ClientConfig.spawnRadius));

                    world.addParticle((IParticleData) ModParticles.DESERT_DUST.get(),
                            particlePos.getX() + rand.nextFloat(),
                            particlePos.getY() + rand.nextFloat(),
                            particlePos.getZ() + rand.nextFloat(),
                            0.9, 0.8, 0.6);
                    continue;
                }

                // Spawn dust particle (at a slower speed) if the biome is a mesa biome
                if (particleBiome.getCategory() == Biome.Category.MESA) {
                    world.addParticle((IParticleData) ModParticles.DESERT_DUST.get(),
                            particlePos.getX() + rand.nextFloat(),
                            particlePos.getY() + rand.nextFloat(),
                            particlePos.getZ() + rand.nextFloat(),
                            0.8, 0.4, 0);
                }
            }
        }
    }
}
