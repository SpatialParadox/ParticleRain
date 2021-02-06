package condolence.particlerain.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class SnowFlakeParticle extends SpriteTexturedParticle {
    protected SnowFlakeParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);

        this.particleGravity = 0.2F;
        this.maxAge = 200;

        this.motionX = -0.05F;
        this.motionY = -0.2F;
        this.motionZ = 0.0F;

        this.particleScale = 0.15F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > this.maxAge - 1/0.06F || this.onGround || this.world.getFluidState(new BlockPos(posX, posY, posZ)).isTagged(FluidTags.WATER)) {
            this.setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() { return IParticleRenderType.PARTICLE_SHEET_OPAQUE; }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSheet;

        public Factory(IAnimatedSprite spriteSheet) {
            this.spriteSheet = spriteSheet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SnowFlakeParticle particle = new SnowFlakeParticle(world, x, y, z);
            particle.selectSpriteRandomly(spriteSheet);
            return particle;
        }
    }
}
