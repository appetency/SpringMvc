package spittr.data;

import spittr.Spittle;

import java.util.List;
/*数据访问*/
public interface SpittleRepository {

  List<Spittle> findRecentSpittles();
/*max参数代表所返回的Spittle中，Spittle ID属性的最大值，而count参数表明要返回多少个Spittle对象*/
  List<Spittle> findSpittles(long max, int count);
  

}
