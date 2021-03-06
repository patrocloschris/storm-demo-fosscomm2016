package my.fosscomm2016.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class CountBolt extends BaseRichBolt {

   private static final long serialVersionUID = 1L;
   private static final Logger LOG = Logger.getLogger(CountBolt.class);

   private OutputCollector collector = null;
   private TopologyContext context = null;
   private Map conf = null;
   private Map<String, Long> counts = new HashMap<>();
   private List<Tuple> tuples = new ArrayList<>();
   private int tickTuple = 1; //Aggregation time period (secs)

   public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
      this.conf = conf;
      this.context = context;
      this.collector = collector;
   }

   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("key", "message"));
   }

   public void execute(Tuple tuple) {
      if (isTickTuple(tuple)) {
         if (!tuples.isEmpty()) {
            LOG.info("Receive a tick Tuple. Sending results to NEXT");
            for (String key : counts.keySet()) {
               String result = key.concat("(" + counts.get(key) + " times)");
               collector.emit(new Values(key, result));
            }
         }
         for (Tuple t : tuples) {
            collector.ack(t);
         }

         tuples.clear();

         //Uncomment this for micro aggregations 
         //counts.clear();

         collector.ack(tuple);
      } else {
         String word = tuple.getString(0);
         Long count = counts.get(word);
         if (count == null)
            count = 0L;

         count++;
         LOG.info("Word=[" + word + "] increasing counter from [" + count + "] to [" + (count - 1) + "]");
         counts.put(word, count);
         tuples.add(tuple);
      }
   }

   private boolean isTickTuple(Tuple tuple) {
      return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
            && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
   }

   @Override
   public Map<String, Object> getComponentConfiguration() {
      if (tickTuple > 0) {
         Config conf = new Config();
         conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, tickTuple);
         return conf;
      } else {
         return super.getComponentConfiguration();
      }
   }
}
