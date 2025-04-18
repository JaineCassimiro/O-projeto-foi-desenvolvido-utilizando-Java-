import { pgTable, text, serial, integer, boolean, timestamp, doublePrecision } from "drizzle-orm/pg-core";
import { createInsertSchema } from "drizzle-zod";
import { z } from "zod";

export const users = pgTable("users", {
  id: serial("id").primaryKey(),
  username: text("username").notNull().unique(),
  password: text("password").notNull(),
});

export const insertUserSchema = createInsertSchema(users).pick({
  username: true,
  password: true,
});

export type InsertUser = z.infer<typeof insertUserSchema>;
export type User = typeof users.$inferSelect;

export const conversionHistory = pgTable("conversion_history", {
  id: serial("id").primaryKey(),
  fromCurrency: text("from_currency").notNull(),
  toCurrency: text("to_currency").notNull(),
  fromAmount: doublePrecision("from_amount").notNull(),
  toAmount: doublePrecision("to_amount").notNull(),
  rate: doublePrecision("rate").notNull(),
  timestamp: timestamp("timestamp").defaultNow().notNull(),
});

export const insertConversionHistorySchema = createInsertSchema(conversionHistory).omit({
  id: true,
  timestamp: true,
});

export type InsertConversionHistory = z.infer<typeof insertConversionHistorySchema>;
export type ConversionHistory = typeof conversionHistory.$inferSelect;
