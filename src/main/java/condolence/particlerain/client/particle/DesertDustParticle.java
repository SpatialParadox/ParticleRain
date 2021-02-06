package condolence.particlerain.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class DesertDustParticle extends SpriteTexturedParticle {
    protected DesertDustParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);

        this.particleGravity = 0.1F;
        this.maxAge = 100;

        this.motionX = -0.3F;
        this.motionY = -0.1F;
        this.motionZ = 0.F;

        this.particleScale = 0.15F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age > this.maxAge || this.prevPosX == this.posX || this.world.getFluidState(new BlockPos(this.posX, this.posY, this.posY)).isTagged(FluidTags.WATER)) {
            this.setExpired();
        }

        if (this.onGround) {
            this.motionY = 0.1F;
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
            DesertDustParticle particle = new DesertDustParticle(world, x, y, z);
            particle.selectSpriteRandomly(spriteSheet);
            return particle;
        }
    }
}
