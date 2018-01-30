#!/usr/bin/env Rscript
library(ggplot2)
library(gridExtra)
library(ggthemes)

#------------- Read data ----------

data = read.csv("./barPlot.csv", header = TRUE,sep=';')

#------------- Generate Graph ----------

plot<-ggplot(data, aes(x = as.numeric(year), y = papers,fill=factor(type)))  +
  theme_bw() +
  scale_fill_grey(start = 0.4, end = 0.8,name = "Type of paper")+
  geom_bar(stat = "identity", position=position_dodge()) +
  geom_text(aes(x=year, y=papers, label=papers),vjust=1.2, 
            position = position_dodge(width=0.95)) +
  ylab("Number of papers of a kind / # papers of a year") + 
  xlab("Year of publication") + 
  theme(legend.position="bottom") +
  scale_x_continuous(breaks=c(2010,2011,2012,2013,2014,2015,2016,2017))  + theme(aspect.ratio=0.5)

#------------- Write to pdf ----------

ggsave("./barPlot.pdf",  width = 10)