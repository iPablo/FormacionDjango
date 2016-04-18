#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import unicode_literals, division
from django.db import models
from django.utils import timezone


class Base(models.Model):
    title = models.CharField(max_length=200)

    class Meta:
        abstract = True


class BaseNews(Base):
    description = models.CharField(max_length=200)

    def __unicode__(self):
        return self.title


class NewsItem(BaseNews):
    publish_date = models.DateTimeField('Fecha publicada')
    owner = models.ForeignKey('auth.User', related_name='newsitem')

    def ha_pasado(self):  # Es un metodo para comprobar que si las noticias
        # tienen mas de dos dias
        # las seleccione y se vea que son antiguas
        ahora = timezone.now()
        diferencia_dias = ahora-self.publish_date
        if diferencia_dias.days > 2:
            return True
        else:
            return False


class Event(BaseNews):
    start_date = models.DateTimeField('Fecha de inicio')
    end_date = models.DateTimeField('Fecha de fin')
    owner = models.ForeignKey('auth.User', related_name='event')

    def dameDuracion(self):  # Devuelve la duracion en dias del evento en cuestion
        if self.end_date < self.start_date:  # Correccion = si por error se metiese una fecha de finalizado antes que la de inicio, la duracion del evento sera de un dia
            self.end_date = self.start_date
        diferencia = self.end_date - self.start_date
        return diferencia.days


class Comment(models.Model):
    news = models.ForeignKey(NewsItem, on_delete=models.CASCADE)
    author = models.CharField(max_length=20, default='Anonimo')
    text = models.TextField()

    def avg(self): #Devuelve la media
        votes = self.vote_set.all()
        count = 0
        for vote in votes:
            count = count + vote.vote
        if count != 0:
            avg = count/votes.count()
        else:
            avg = 0
        return avg


class Vote(models.Model):
    user = models.ForeignKey('auth.User', related_name='voter')
    comment = models.ForeignKey(Comment, on_delete=models.CASCADE)
    vote = models.IntegerField(default=1)

    class Meta:
        unique_together = ('user', 'comment')
