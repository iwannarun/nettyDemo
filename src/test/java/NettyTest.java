import com.xhuabu.netty.handler.NettyHandler;
import com.xhuabu.netty.server.NettyServer;
import io.netty.channel.Channel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述:
 *
 * @author 陈润发
 * @created 16/9/3
 * @since v1.0.0
 */
public class NettyTest {
    public static void main(String args[]){

        try {
            NettyServer.start(new NettyHandler() {
                AtomicInteger ai=new AtomicInteger();
                @Override
                public void handleMsg(final Channel channel, String json) {
                    //System.out.println(ai.incrementAndGet()+" "+System.currentTimeMillis()+" "+json);
                    System.out.println("服务端1向客户端 输入数据");

                    Timer time = new Timer();
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            channel.writeAndFlush("1接受数据"+ai.incrementAndGet());
                        }
                    },1000l,5000l);

                }

                @Override
                public void channelRemoved(Channel channel) {
                    System.out.println("1remove");
                }

                @Override
                public void channelRegistered(Channel channel) {
                    channel.writeAndFlush("1初次握手");
                    System.out.println("1有新的客户端连入");
                }
            }, 1, 33331);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
