package my.fosscomm2016.demo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SplitBolt extends BaseRichBolt {

   private static final long serialVersionUID = 1L;
   private static final Logger LOG = Logger.getLogger(SplitBolt.class);

   private OutputCollector collector = null;
   private TopologyContext context = null;
   private Map conf = null;

   public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
      this.conf = conf;
      this.context = context;
      this.collector = collector;
   }

   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("word", "count"));
   }

   public void execute(Tuple tuple) {
      String sentence = tuple.getString(0);
      LOG.info("Recieve a new sentence");
      for (String word : sentence.split("\\s+")) {
         collector.emit(new Values(word, 1));
      }
      collector.ack(tuple);
   }

}
