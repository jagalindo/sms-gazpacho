#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)

script.dir <- dirname(sys.frame(1)$ofile)
setwd(script.dir);
#------------- Read data ----------

data = read.csv("../data_marga/trends.csv", header = TRUE,sep=';')
map_facets<-c(
  "k-evaluation" = "Evaluation\nResearch", 
  "k-experience" = "Experience\nReport",
  "k-opinion" = "Opinion\nPaper",
  "k-philosophical" = "Philosophical\nPaper",
  "k-solution" = "Solution\nProposal",
  "k-validation" = "Validation\nResearch",
  "k-vis" = "Variability-intensive systems\nanalysis",
  "k-testing" = "Testing and\nevolution",
  "k-reverse" = "Reverse\nengineering",
  "k-mmodel" = "Multi-model\nvariability analysis",
  "k-modeling" = "Variability \nmodelling",
  "k-configuration" = "Product configuration\nand derivation"
)

#Order like in the rest of sections
#data$type <- factor(data$type, levels = c("k-configuration","k-testing","k-reverse","k-mmodel","k-modeling","k-vis"))

#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = as.numeric(year), y = papers))  +
  theme_bw() +
  geom_line(aes(linetype=factor(type)),stat = "identity") +
  geom_point(mapping=aes(x=as.numeric(year), y=papers, shape=factor(type)), size=3)+
  scale_linetype_discrete(labels=map_facets,guide=FALSE)+
  scale_shape_discrete(labels=map_facets)+
  scale_y_continuous(labels = scales::percent)+
  ylab("# variability context facet papers/ # year papers") + 
  xlab("Year of publication") + 
  labs(shape = "Variablity facet")+
  theme(legend.position="bottom",legend.text=element_text(size=15)) +
  scale_x_continuous(breaks=c(2010,2011,2012,2013,2014,2015,2016,2017)) + theme(aspect.ratio=0.45)+
  theme(text = element_text(size=13),axis.text.x = element_text(angle=60, hjust=1))+guides(shape=guide_legend(nrow=1,byrow=TRUE))
#------------- Write to pdf ----------

ggsave("../data_marga/trends.pdf",  width = 12)