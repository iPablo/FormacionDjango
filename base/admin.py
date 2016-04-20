#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.contrib import admin
from .models import NewsItem, Event


class NewsItemAdmin(admin.ModelAdmin):
    list_display = ('title', 'description', 'publish_date')
    search_fields = ['title', 'description', 'publish_date']
    list_filter = ('publish_date',)


class EventAdmin(admin.ModelAdmin):
    list_display = ('title', 'description', 'start_date', 'end_date')
    search_fields = ['title', 'description', 'start_date', 'end_date']
    list_filter = ('start_date', 'end_date')

admin.site.register(NewsItem, NewsItemAdmin)
admin.site.register(Event, EventAdmin)
