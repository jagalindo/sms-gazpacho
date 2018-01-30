#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)


#------------- Read data ----------

data = read.csv("./trendsEvolution.csv", header = TRUE,sep=';')

map_facets<-c(
  "SU"="Survey",
  "C11"="C11 - Software quality",
  "C10"="C10 - Software engineering tools and methods",
  "C12"="C12 - Related Disciplines of Software Engineering",
  "C2"="C2 - Software requirements",
  "CS"="Case Study",
  "C3"="C3 - Software design",
  "C4"="C4 - Software construction",
  "C5"="C5 - Software testing",
  "EX"="Experiment",
  "C6"="C6 - Software maintenance",
  "QE"="Quasi-experiment",
  "C7"="C7 - Software configuration management",
  "C8"="C8 - Software engineering management",
  "C9"="C9 - Software engineering process"
)

#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = as.numeric(year), y = papers))  +
  theme_bw() +
  geom_line(aes(linetype=factor(type)),stat = "identity") +
  geom_point(mapping=aes(x=as.numeric(year), y=papers, shape=factor(type)), size=3)+
  scale_linetype_discrete(labels=map_facets,guide=FALSE)+
  scale_shape_discrete(labels=map_facets)+
  scale_y_continuous(labels = scales::percent)+
  ylab("# Knowledge Areas (KAs) papers/ # year papers") + 
  xlab("Year of publication") + 
  labs(shape = "Knowledge Areas (KAs)")+
  theme(legend.position="bottom",legend.text=element_text(size=15)) +
  scale_x_continuous(breaks=c(2010,2011,2012,2013,2014,2015,2016,2017)) + theme(aspect.ratio=0.45)+
  theme(text = element_text(size=13),axis.text.x = element_text(angle=60, hjust=1))+guides(shape=guide_legend(nrow=1,byrow=TRUE))
#------------- Write to pdf ----------

ggsave("./trendsEvolution.pdf", plot, width = 12)