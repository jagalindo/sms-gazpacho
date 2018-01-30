#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)

#script.dir <- dirname(sys.frame(1)$ofile)
#setwd(script.dir);
#------------- Read data ----------

data = read.csv("${data.origin}", header = TRUE,sep=';')

map_facets<-c(
<#list data.names as propName, propValue>
  "${propName}"="${propValue}"<#if propValue?has_next>,</#if>
</#list>
)

#Order like in the rest of sections
#data$FacetA <- factor(data$FacetA, levels = c(<#list data.dimension_1 as facet>"${facet}"<#if facet?has_next>,</#if></#list>))
#data$FacetB <- factor(data$FacetB, levels = c(<#list data.dimension_2 as facet>"${facet}"<#if facet?has_next>,</#if></#list>))

plot<-ggplot(data, aes(x=faceta, y=facetb)) + 
  geom_tile(aes(fill = papers),colour = "white") + 
  geom_text(aes(label = papers),size=5) +
  scale_fill_gradient(high="#707070", low="white",name = "Number of papers") +
  ylab("${data.dimension1_name}") + 
  xlab("${data.dimension2_name}")+ 
  theme(legend.position="right") +
  scale_x_discrete(labels=map_facets)+
  scale_y_discrete(labels=map_facets)+
  theme(axis.text=element_text(size=7), axis.title=element_text(size=9,face="bold"))+
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(), 
        panel.background = element_blank())+ theme(aspect.ratio=0.5)+
  theme(axis.text.x=element_text(angle=45,hjust=1)) 

#------------- Write to pdf ----------

ggsave("${data.destination}", plot, width = 10, height = 4)