package epicsquid.roots.entity;

import epicsquid.roots.ritual.RitualRegistry;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityRitualNaturalAura extends EntityRitualBase {

  public EntityRitualNaturalAura(World worldIn) {
    super(worldIn);
    getDataManager().register(lifetime, RitualRegistry.ritual_natural_aura.duration + 20);
  }

  @Override
  public void onUpdate() {
    ticksExisted++;
    float alpha = (float) Math.min(40, (RitualRegistry.ritual_life.duration + 20) - getDataManager().get(lifetime)) / 40.0f;
    getDataManager().set(lifetime, getDataManager().get(lifetime) - 1);
    getDataManager().setDirty(lifetime);
    if (getDataManager().get(lifetime) < 0) {
      setDead();
    }
    if (world.isRemote && getDataManager().get(lifetime) > 0) {
      //todo: fix particle when available | ParticleUtil.spawnParticleStar(world, (float)posX, (float)posY, (float)posZ, 0, 0, 0, 100, 255, 100, 0.5f*alpha, 20.0f, 40);
      if (rand.nextInt(5) == 0) {
        //todo: fix particle when available | ParticleUtil.spawnParticleSpark(world, (float)posX, (float)posY, (float)posZ, 0.125f*(rand.nextFloat()-0.5f), 0.0625f*(rand.nextFloat()), 0.125f*(rand.nextFloat()-0.5f), 100, 255, 100, 1.0f*alpha, 1.0f+rand.nextFloat(), 160);
      }
      for (float i = 0; i < 360; i += 72.0f) {
        double ang = ticksExisted % 360;
        float tx = (float) posX + 1.0f * (float) Math.sin(Math.toRadians(i + ang));
        float ty = (float) posY;
        float tz = (float) posZ + 1.0f * (float) Math.cos(Math.toRadians(i + ang));
        //todo: fix particle when available | ParticleUtil.spawnParticleGlow(world, tx, ty, tz, 0, 0, 0, 100, 255, 100, 0.5f*alpha, 8.0f, 40);
      }
    }
    if (this.ticksExisted % 5 == 0) {
      BlockPos pos = world.getTopSolidOrLiquidBlock(getPosition().add(rand.nextInt(19) - 9, 0, rand.nextInt(19) - 9));
      IBlockState state = world.getBlockState(pos);
      if (state.getBlock() instanceof BlockCrops) {
        if (((BlockCrops) state.getBlock()).canGrow(world, pos, state, world.isRemote)) {
          world.setBlockState(pos, state.getBlock().getStateFromMeta(state.getBlock().getMetaFromState(state) + 1));
          world.notifyBlockUpdate(pos, state, state.getBlock().getStateFromMeta(state.getBlock().getMetaFromState(state) + 1), 8);
          if (world.isRemote) {
            for (float i = 0; i < 1; i += 0.125f) {
              float coeff = i;
              //todo: fix particle when available | ParticleUtil.spawnParticleSpark(world, (pos.getX()+0.5f), (pos.getY()+0.5f)+i, (pos.getZ()+0.5f), 0.125f*(rand.nextFloat()-0.5f), 0.0625f*(rand.nextFloat()), 0.125f*(rand.nextFloat()-0.5f), 100, 255, 100, 1.0f*(1.0f-coeff)*alpha, 3.0f*(1.0f-coeff), 40);
            }
          }
        }
      }
      if (state.getBlock() instanceof BlockSapling) {
        BlockSapling blockSapling = (BlockSapling) state.getBlock();
        blockSapling.grow(world, pos, state, rand);
        if (world.isRemote) {
          for (float i = 0; i < 1; i += 0.125f) {
            float coeff = i;
            //todo: fix particle when available | ParticleUtil.spawnParticleSpark(world, (pos.getX()+0.5f), (pos.getY()+0.5f)+i, (pos.getZ()+0.5f), 0.125f*(rand.nextFloat()-0.5f), 0.0625f*(rand.nextFloat()), 0.125f*(rand.nextFloat()-0.5f), 100, 255, 100, 1.0f*(1.0f-coeff)*alpha, 3.0f*(1.0f-coeff), 40);
          }
        }
      }
    }
  }
}
