package collisions;

@FunctionalInterface
public interface CollisionEventNotifier {
    void notifyCollision(CollisionEvent event);
}
