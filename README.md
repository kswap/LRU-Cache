# LRU-Cache
Implementation of concurrent LRU Cache supporting get,put and eviction of old keys.

The data structure used are ConcurrentLinkedQueue and ConcurrentHashMap. These two data structure with read and write lock are used to
make the implementation thread safe. It can be used in multithreading environment.

## API

- ### LruCache(cacheSize)

  Creates a new LRU Cache that stores cacheSize elements before removing the least recently used.
  
- ### V get(K Key)
  Query the value of the key and mark the key as most recently used.
  #### Returns:
  Value of the key if found, otherwise it returns null.
  
- ### put(K key, V value)
  Set the value of the key and mark the key as most recently used.
  
- ### V evict(K key)
  Remove the value from the cache.
  #### Returns: 
  Value of the key if found, otherwise it returns null.
  
- ### cacheSize
  Size of the LRU Cache.
  
