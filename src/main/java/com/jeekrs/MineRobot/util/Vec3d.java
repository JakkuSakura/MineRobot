package com.jeekrs.MineRobot.util;

import net.minecraft.util.math.MathHelper;

public class Vec3d
{
    /** X coordinate of Vec3D */
    public double x;
    /** Y coordinate of Vec3D */
    public double y;
    /** Z coordinate of Vec3D */
    public double z;
    /**
     * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other static
     * method which creates and places it in the list.
     */
    /*public static Vec3d createVectorHelper(double p_72443_0_, double p_72443_2_, double p_72443_4_)
    {
        return new Vec3d(p_72443_0_, p_72443_2_, p_72443_4_);
    }*/
    
    public Vec3d(Vec3d pos) {
    	this(pos.x, pos.y, pos.z);
    }
    
    public Vec3d(net.minecraft.util.math.Vec3d pos) {
    	this(pos.x, pos.y, pos.z);
    }
    
    public Vec3d(BlockPos pos) {
    	this(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vec3d(double p_i1108_1_, double p_i1108_3_, double p_i1108_5_)
    {
        if (p_i1108_1_ == -0.0D)
        {
            p_i1108_1_ = 0.0D;
        }

        if (p_i1108_3_ == -0.0D)
        {
            p_i1108_3_ = 0.0D;
        }

        if (p_i1108_5_ == -0.0D)
        {
            p_i1108_5_ = 0.0D;
        }

        this.x = p_i1108_1_;
        this.y = p_i1108_3_;
        this.z = p_i1108_5_;
    }

    /**
     * Sets the x,y,z components of the vector as specified.
     */
    protected Vec3d setComponents(double p_72439_1_, double p_72439_3_, double p_72439_5_)
    {
        this.x = p_72439_1_;
        this.y = p_72439_3_;
        this.z = p_72439_5_;
        return this;
    }

    /**
     * Returns a new vector with the result of the specified vector minus this.
     */
    public Vec3d subtract(Vec3d p_72444_1_)
    {
        /**
         * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other
         * static method which creates and places it in the list.
         */
        return new Vec3d(p_72444_1_.x - this.x, p_72444_1_.y - this.y, p_72444_1_.z - this.z);
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Vec3d normalize()
    {
        double d0 = (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d0 < 1.0E-4D ? new Vec3d(0.0D, 0.0D, 0.0D) : new Vec3d(this.x / d0, this.y / d0, this.z / d0);
    }

    public double dotProduct(Vec3d p_72430_1_)
    {
        return this.x * p_72430_1_.x + this.y * p_72430_1_.y + this.z * p_72430_1_.z;
    }

    /**
     * Returns a new vector with the result of this vector x the specified vector.
     */
    public Vec3d crossProduct(Vec3d p_72431_1_)
    {
        /**
         * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other
         * static method which creates and places it in the list.
         */
        return new Vec3d(this.y * p_72431_1_.z - this.z * p_72431_1_.y, this.z * p_72431_1_.x - this.x * p_72431_1_.z, this.x * p_72431_1_.y - this.y * p_72431_1_.x);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public Vec3d addVector(double p_72441_1_, double p_72441_3_, double p_72441_5_)
    {
        /**
         * Static method for creating a new Vec3D given the three x,y,z values. This is only called from the other
         * static method which creates and places it in the list.
         */
        return new Vec3d(this.x + p_72441_1_, this.y + p_72441_3_, this.z + p_72441_5_);
    }

    /**
     * Euclidean distance between this and the specified vector, returned as double.
     */
    public double distanceTo(Vec3d p_72438_1_)
    {
        double d0 = p_72438_1_.x - this.x;
        double d1 = p_72438_1_.y - this.y;
        double d2 = p_72438_1_.z - this.z;
        return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    /**
     * The square of the Euclidean distance between this and the specified vector.
     */
    public double squareDistanceTo(Vec3d p_72436_1_)
    {
        double d0 = p_72436_1_.x - this.x;
        double d1 = p_72436_1_.y - this.y;
        double d2 = p_72436_1_.z - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    /**
     * The square of the Euclidean distance between this and the vector of x,y,z components passed in.
     */
    public double squareDistanceTo(double p_72445_1_, double p_72445_3_, double p_72445_5_)
    {
        double d3 = p_72445_1_ - this.x;
        double d4 = p_72445_3_ - this.y;
        double d5 = p_72445_5_ - this.z;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    /**
     * Returns the length of the vector.
     */
    public double lengthVector()
    {
        return (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Returns a new vector with x value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3d getIntermediateWithXValue(Vec3d p_72429_1_, double p_72429_2_)
    {
        double d1 = p_72429_1_.x - this.x;
        double d2 = p_72429_1_.y - this.y;
        double d3 = p_72429_1_.z - this.z;

        if (d1 * d1 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (p_72429_2_ - this.x) / d1;
            return d4 >= 0.0D && d4 <= 1.0D ? new Vec3d(this.x + d1 * d4, this.y + d2 * d4, this.z + d3 * d4) : null;
        }
    }

    /**
     * Returns a new vector with y value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3d getIntermediateWithYValue(Vec3d p_72435_1_, double p_72435_2_)
    {
        double d1 = p_72435_1_.x - this.x;
        double d2 = p_72435_1_.y - this.y;
        double d3 = p_72435_1_.z - this.z;

        if (d2 * d2 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (p_72435_2_ - this.y) / d2;
            return d4 >= 0.0D && d4 <= 1.0D ? new Vec3d(this.x + d1 * d4, this.y + d2 * d4, this.z + d3 * d4) : null;
        }
    }

    /**
     * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    public Vec3d getIntermediateWithZValue(Vec3d p_72434_1_, double p_72434_2_)
    {
        double d1 = p_72434_1_.x - this.x;
        double d2 = p_72434_1_.y - this.y;
        double d3 = p_72434_1_.z - this.z;

        if (d3 * d3 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (p_72434_2_ - this.z) / d3;
            return d4 >= 0.0D && d4 <= 1.0D ? new Vec3d(this.x + d1 * d4, this.y + d2 * d4, this.z + d3 * d4) : null;
        }
    }

    public String toString()
    {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    /**
     * Rotates the vector around the x axis by the specified angle.
     */
    public void rotateAroundX(float p_72440_1_)
    {
        float f1 = MathHelper.cos(p_72440_1_);
        float f2 = MathHelper.sin(p_72440_1_);
        double d0 = this.x;
        double d1 = this.y * (double)f1 + this.z * (double)f2;
        double d2 = this.z * (double)f1 - this.y * (double)f2;
        this.setComponents(d0, d1, d2);
    }

    /**
     * Rotates the vector around the y axis by the specified angle.
     */
    public void rotateAroundY(float p_72442_1_)
    {
        float f1 = MathHelper.cos(p_72442_1_);
        float f2 = MathHelper.sin(p_72442_1_);
        double d0 = this.x * (double)f1 + this.z * (double)f2;
        double d1 = this.y;
        double d2 = this.z * (double)f1 - this.x * (double)f2;
        this.setComponents(d0, d1, d2);
    }

    /**
     * Rotates the vector around the z axis by the specified angle.
     */
    public void rotateAroundZ(float p_72446_1_)
    {
        float f1 = MathHelper.cos(p_72446_1_);
        float f2 = MathHelper.sin(p_72446_1_);
        double d0 = this.x * (double)f1 + this.y * (double)f2;
        double d1 = this.y * (double)f1 - this.x * (double)f2;
        double d2 = this.z;
        this.setComponents(d0, d1, d2);
    }
    
    public net.minecraft.util.math.Vec3d toMCVec() {
    	return new net.minecraft.util.math.Vec3d(this.x, this.y, this.z);
    }
    
    public BlockPos toBlockPos() {
    	return new BlockPos(this.x, this.y, this.z);
    }
}