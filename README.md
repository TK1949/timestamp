# timestamp
高并发时间戳实现/High concurrent timestamp implementation

System.currentTimeMillis()是极其常用的基础Java API，广泛地用来获取时间戳或测量代码执行时长等，在我们的印象中应该快如闪电。但实际上在并发调用或者特别频繁调用它的情况下（比如一个业务繁忙的接口，或者吞吐量大的需要取得时间戳的流式程序），其性能表现会令人大跌眼镜。

主要原因是因为：

调用gettimeofday()需要从用户态切换到内核态；

gettimeofday()的表现受Linux系统的计时器（时钟源）影响，在HPET计时器下性能尤其差；

系统只有一个全局时钟源，高并发或频繁访问会造成严重的争用。

HPET计时器性能较差的原因是会将所有对时间戳的请求串行执行。TSC计时器性能较好，因为有专用的寄存器来保存时间戳。缺点是可能不稳定，因为它是纯硬件的计时器，频率可变（与处理器的CLK信号有关）。关于HPET和TSC的细节可以参见https://en.wikipedia.org/wiki/HighPrecisionEventTimer与https://en.wikipedia.org/wiki/TimeStamp_Counter。

最常见的办法是用单个调度线程来按毫秒更新时间戳，相当于维护一个全局缓存。其他线程取时间戳时相当于从内存取，不会再造成时钟资源的争用，代价就是牺牲了一些精确度。