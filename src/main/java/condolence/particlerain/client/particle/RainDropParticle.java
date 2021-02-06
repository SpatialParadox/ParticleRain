package condolence.particlerain.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;

public class RainDropParticle extends SpriteTexturedParticle {
    protected RainDropParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);

        particleGravity = 0.7F;
        maxAge = 200;

        motionX = 0.0F;
        motionY = -0.7F;
        motionZ = 0.0F;

        particleScale = 0.15F;
    }


    @Override
    public void tick() {
        super.tick();

        // Remove particle if maxAge is exceeded / particle is on ground / particle is in water
        if (age > maxAge - 1/0.06F || onGround || world.getFluidState(new BlockPos(posX, posY, posZ)).isTagged(FluidTags.WATER)) { this.setExpired(); }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static final class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RainDropParticle particle = new RainDropParticle(world, x, y, z);
            particle.selectSpriteRandomly(spriteSet);
            return particle;
        }
    }
}
