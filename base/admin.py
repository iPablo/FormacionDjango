#!/usr/bin/env python
# -*- coding: utf-8 -*-
from django.contrib import admin

from .models import NewsItem, Event

admin.site.register(NewsItem)
admin.site.register(Event)
