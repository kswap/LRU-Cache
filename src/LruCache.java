import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LruCache<K, V> {

	int cacheSize = 0;

	private ConcurrentLinkedQueue<K> queue = new ConcurrentLinkedQueue<K>();

	private ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	private Lock writeLock = lock.writeLock();

	private Lock readLock = lock.readLock();

	public LruCache(final int size) {
		this.cacheSize = size;
	}

	/**
	 * @param key
	 * @return Value corresponding to the Key K in the map is returned or null
	 *         is returned if the key is not present in the map
	 */
	public V get(K key) {

		readLock.lock();
		try {
			V val = null;
			if (map.contains(key)) {
				queue.remove(key);
				val = map.get(key);
				queue.add(key);
			}
			return val;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * @param key
	 * @param value
	 *            Read and write lock with ConcurrentHashMap and
	 *            ConcurrentLinkedQueue to implement concurrent Lru Cache. Entry
	 *            with key k and value v is added to the Lru Cache.
	 */
	public void put(K key, V value) {
		writeLock.lock();
		try {
			if (map.contains(key)) {
				queue.remove(key);
			}
			if (queue.size() == cacheSize) {
				K queueKey = queue.poll();
				map.remove(queueKey);
			}
			queue.add(key);
			map.put(key, value);

		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @param key
	 * @return Value corresponding to the key is returned if present in the map
	 *         else null is returned. Entry with key k is removed from the
	 *         cache.
	 */
	public V evict(K key) {
		writeLock.lock();
		try {
			V val = null;
			if (map.contains(key)) {
				val = map.remove(key);
				queue.remove(key);
			}
			return val;
		} finally {
			writeLock.unlock();
		}
	}

}
