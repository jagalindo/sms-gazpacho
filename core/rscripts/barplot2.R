#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)

script.dir <- dirname(sys.frame(1)$ofile)
setwd(script.dir);
#------------- Read data ----------

data = read.csv("../output_data/barplot2.csv", header = TRUE,sep=';')

map_facets<-c(
  "k-evaluation" = "Evaluation\nResearch", 
  "k-experience" = "Experience\nReport",
  "k-opinion" = "Opinion\nPaper",
  "k-philosophical" = "Philosophical\nPaper",
  "k-solution" = "Solution\nProposal",
  "k-validation" = "Validation\nResearch",
  "k-vis" = "Beyond product\nline analysis",
  "k-testing" = "Testing and\nevolution",
  "k-reverse" = "Reverse\nengineering",
  "k-mmodel" = "Multi-model\nvariability analysis",
  "k-modeling" = "Variability and\nmodeling expressivenes",
  "k-configuration" = "Product configuration\nand derivation"
)


#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = as.numeric(year), y = papers))  +
  theme_bw() +
  geom_line(aes(color=factor(type)),stat = "identity") +
  scale_color_discrete(labels=map_facets)+
  ylab("# variability facet papers/ # year papers") + 
  xlab("Year of publication") + 
  labs(color = "Variablity facet")+
  theme(legend.position="bottom") +
  scale_x_continuous(breaks=c(2010,2011,2012,2013,2014,2015,2016))  + theme(aspect.ratio=0.5)+
  theme(text = element_text(size=10),axis.text.x = element_text(angle=60, hjust=1))
#------------- Write to pdf ----------

ggsave("temporal_var.pdf",  width = 10)