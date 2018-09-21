1) Heap is divided into  
    a) Young Generation (Further divided into Eden/ s1 & s2 generation).  
        a) When a GC is run - live objects from eden & s1 is moved to s2 OR from eden & s2 is moved to s1  
          (because of fragmentation optimizations).  
    b) Tenured Generation  
    c) Perm Generation (Metaspace in Java8)
    
2) Different kinds of GC Algs  
    a) G1 GC - Designed for Application running on multi processor machines, with huge RAM. Divides the heap into a set of
          heap regions, & marks the regions that are mostly empty during Marking phase. These mostly empty heap regions are
          collected first.  
    b) Parallel - Uses multiple threads. freezes other application threads. can specify max pause time, through put 
        and footprint.  
    c) Concurrent Mark & Sweep - Uses multiple threads. runs along Application threads.  
    d) Serial - Single thread, freezes all application threads when it runs. Not good for App server env.  
    
3) Tools:  
    a) Eclipse MAT for heap analysis.  
    c) JVM Configuration to take a heap dump on JVM crash.  
    
4) java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -Xloggc:/home/ravi/devapps/gc.log -jar target/jvm-0.0.1-SNAPSHOT.jar
