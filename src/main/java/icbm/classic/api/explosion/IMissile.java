package icbm.classic.api.explosion;


import icbm.classic.lib.transform.vector.Pos;

/** This is an interface applied by all missile entities. You may cast this into an @Entity. The
 * "set" version of the function will make the entity do the action on the next tick.
 *
 * @author Calclavia */
@Deprecated //Will be recoded
public interface IMissile extends IExplosiveContainer
{
    /** Blows up this missile. It will detonate the missile with the appropriate explosion. */
    void explode();

    void setExplode();

    boolean isExploding();

    /** Blows up this missile like a TNT explosion. Small explosion used for events such as a missile
     * crashing or failure to explode will result in this function being called. */
    void normalExplode();

    void setNormalExplode();

    /** Drops the specified missile as an item. */
    void dropMissileAsItem();

    /** The amount of ticks this missile has been flying for. Returns -1 if the missile is not
     * flying. */
    int getTicksInAir();

    /** Gets the launcher this missile is launched from. */
    ILauncherContainer getLauncher();

    /** Launches the missile into a specific target.
     *
     * @param target */
    void launch(Pos target);

    void launch(Pos target, int height);
}