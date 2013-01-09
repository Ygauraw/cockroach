package gark.tap.cockroach.mathengine.movingobjects;

import gark.tap.cockroach.ResourceManager;

import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.PointF;

public abstract class ArmedMovingObject extends MovingObject {

//    protected static final float PI = (float) Math.PI;
//    protected static final float DEG_TO_PI = (float) (Math.PI / 180.0);

//    protected long mLastShoot;
//    protected int mTimeRecharge;

//    protected ResourceManager mResourceManager;

    public ArmedMovingObject(PointF point, TiledTextureRegion mainTextureRegion, ResourceManager resourceManager) {
        super(point, mainTextureRegion, resourceManager.getVertexBufferObjectManager());
//        mResourceManager = resourceManager;
    }

    @Override
    public void tact(long now, long period) {

    }

//    public boolean canShoot(long now) {
//        if (now - mLastShoot > mTimeRecharge) {
//            return true;
//        }
//        return false;
//    }
//
//    public abstract List<FlyingObject> shoot(long now);
//
//    public Bomb addBomb(float angle) {
//        float tX = (float) (posX() + (Config.CAMERA_WIDTH * Math.cos(angle * DEG_TO_PI - PI / 2)));
//        float tY = (float) (posY() + (Config.CAMERA_WIDTH * Math.sin(angle * DEG_TO_PI - PI / 2)));
//        return new Bomb(new PointF(posX(), posY()), new PointF(tX, tY), angle,
//                mResourceManager.getBomb(), mResourceManager.getVertexBufferObjectManager());
//    }
//
//    public Bullet addBullet(float angle) {
//        return addBullet(posX(), posY(), angle);
//    }
//
//    public Bullet addBullet(float sX, float sY, float angle) {
//        float tX = (float) (sX + (Config.CAMERA_WIDTH * Math.cos(angle * DEG_TO_PI - PI / 2)));
//        float tY = (float) (sY + (Config.CAMERA_WIDTH * Math.sin(angle * DEG_TO_PI - PI / 2)));
//        return new Bullet(new PointF(sX, sY), new PointF(tX, tY), angle, mResourceManager.getBullet(),
//                mResourceManager.getVertexBufferObjectManager());
//    }
}
